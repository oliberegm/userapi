package com.oliber.userapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliber.userapi.dto.request.LoginRequestDTO;
import com.oliber.userapi.dto.request.PhoneRequestDTO;
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
class LoginUseCaseIntegrationTest {

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
    void login() throws Exception {
        String mail = "pruebas@test.com";
        userRepository.save(dataUserTest());
        LoginRequestDTO login = LoginRequestDTO.builder()
            .email(mail)
            .password("pass123")
            .build();
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk());
    }

    @Test
    void loginWithInvalidEmail() throws Exception {
        String mail = "prueba@test1.com";
        userRepository.save(dataUserTest());
        LoginRequestDTO login = LoginRequestDTO.builder()
            .email(mail)
            .password("Pruebas1234")
            .build();
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized());
    }

}
