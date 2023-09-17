package com.oliber.userapi.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliber.userapi.dto.request.PhoneRequestDTO;
import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.mapper.PhoneMapper;
import com.oliber.userapi.model.User;
import com.oliber.userapi.repository.UserRepository;
import com.oliber.userapi.service.security.ITokenService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ITokenService tokenService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    private User dataUserTest() {
        return new User(
            UUID.randomUUID(),
            "pruebas tests",
            "pruebas@test.com",
            "pass123",
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
            tokenService.generateToken("pruebas@test.com"),
            Boolean.TRUE,
            List.of(PhoneMapper.toEntity(PhoneRequestDTO.builder()
                    .contrycode("57")
                    .citycode("1")
                    .number("1234567")
                .build()))
        );
    }

    @Test
    void registerUser() throws Exception {
        String mail = "prueba@test.com";
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
            .email(mail)
            .name("pruebas tests")
            .password("Pruebas1234")
            .phones(List.of(PhoneRequestDTO.builder()
                    .contrycode("57")
                    .citycode("1")
                    .number("1234567")
                .build()))
            .build();
        mockMvc.perform(post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRequestDTO)))
            .andExpect(status().isCreated());
        assertThat(userRepository.findByEmail(mail)).isNotEmpty();
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user = userRepository.save(dataUserTest());

        mockMvc.perform(get("/users")
            .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
    }

    @Test
    void testGetUserByEmailNotFound() throws Exception {
        User user = userRepository.save(dataUserTest());
        String mail = "prueba@test.com";
        mockMvc.perform(get("/users/byEmail")
                .header("Authorization", "Bearer " + user.getToken())
                .param("email", mail)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        User user = userRepository.save(dataUserTest());
        String mail = "pruebas@test.com";
        mockMvc.perform(get("/users/byEmail")
                .header("Authorization", "Bearer " + user.getToken())
                .param("email", mail)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        User user = userRepository.save(dataUserTest());
        mockMvc.perform(get("/users/" + user.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = userRepository.save(dataUserTest());
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
            .email("pruebas@gmas.com")
            .name("pruebas tests")
            .password("Pruebas1234")
            .phones(List.of(PhoneRequestDTO.builder()
                .contrycode("57")
                .citycode("1")
                .number("1234567")
                .build()))
            .build();
        mockMvc.perform(put("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + user.getToken())
                .content(objectMapper.writeValueAsString(userRequestDTO))
            )
            .andExpect(status().isOk());
        User user2 = userRepository.findById(user.getId()).get();
        assertThat(user2.getModified()).isNotEqualTo(user.getModified());
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = userRepository.save(dataUserTest());

        mockMvc.perform(delete("/users/" + user.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void testActivateUser() throws Exception {
        User user = userRepository.save(dataUserTest());
        mockMvc.perform(patch("/users/" + user.getId() + "/activate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
        assertThat(userRepository.findById(user.getId()).get().getIsActive()).isEqualTo(Boolean.TRUE);
    }

    @Test
    void testDeactivateUser() throws Exception {
        User user = userRepository.save(dataUserTest());
        mockMvc.perform(patch("/users/" + user.getId() + "/deactivate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
        assertThat(userRepository.findById(user.getId()).get().getIsActive()).isEqualTo(Boolean.FALSE);
    }

}
