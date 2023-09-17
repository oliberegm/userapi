package com.oliber.userapi.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.oliber.userapi.dto.request.PhoneRequestDTO;
import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.errors.exception.EmailAlreadyExistsException;
import com.oliber.userapi.errors.exception.NotFoundException;
import com.oliber.userapi.mapper.PhoneMapper;
import com.oliber.userapi.model.User;
import com.oliber.userapi.repository.UserRepository;
import com.oliber.userapi.service.security.ITokenService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ITokenService tokenService;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, tokenService);
    }

    @Test
    void testGetAllUsers() {
        // Simular el comportamiento del repositorio para devolver una lista de usuarios
        List<User> userList = List.of(dataUserTest()); // Configura la lista de usuarios de prueba
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();
        assertEquals(userList, result);
    }

    @Test
    void testGetUserById() {
        UUID userId = dataUserTest().getId();
        User user = dataUserTest();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId.toString());
        assertEquals(user, result);
    }

    @Test
    void testGetUserByIdNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId.toString()));
    }

    @Test
    void testCreateUser() {
        // Configurar un usuario de prueba y su solicitud
        UserRequestDTO userRequest = dataUserRequestTest();
        // Simular el comportamiento del repositorio y el servicio de tokens
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(tokenService.generateToken(userRequest.getEmail())).thenReturn("generated_token");
        when(userRepository.save(any(User.class))).thenReturn(dataUserTest());

        User createdUser = userService.createUser(userRequest);

        assertNotNull(createdUser);
        assertEquals("pruebas tests", createdUser.getName());
        assertEquals("pruebas@test.com", createdUser.getEmail());
        assertEquals("generated_token", createdUser.getToken());
        assertTrue(createdUser.getIsActive());
    }

    @Test
    void testCreateUserEmailAlreadyExists() {
        // Configurar un usuario de prueba y su solicitud
        UserRequestDTO userRequest = dataUserRequestTest();
        // Simular el comportamiento del repositorio para un email ya existente
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void testDeleteUser() {
        UUID userId = dataUserTest().getId();

        // Simular el comportamiento del repositorio para eliminar el usuario
        when(userRepository.findById(userId)).thenReturn(Optional.of(dataUserTest()));

        userService.deleteUser(userId.toString());

        // Verificar que el método deleteById fue llamado con el ID correcto
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testActivateUser() {
        UUID userId = dataUserTest().getId();

        // Simular el comportamiento del repositorio para activar el usuario
        when(userRepository.findById(userId)).thenReturn(Optional.of(dataUserTest()));

        userService.activateUser(userId.toString());

        // Verificar que el método changeStatusUser fue llamado con el estado correcto
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @Test
    void testDeactivateUser() {
        UUID userId = dataUserTest().getId();

        // Simular el comportamiento del repositorio para desactivar el usuario
        when(userRepository.findById(userId)).thenReturn(Optional.of(dataUserTest()));

        userService.deactivateUser(userId.toString());

        // Verificar que el método changeStatusUser fue llamado con el estado correcto
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testFindByEmailAndPassword() {
        String email = "user@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        // Simular el comportamiento del repositorio para encontrar un usuario por email y contraseña
        when(userRepository.findByEmailAndPassword(email, password)).thenReturn(Optional.of(user));

        User result = userService.findByEmailAndPassword(email, password);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
    }

    @Test
    public void testFindByEmailAndPasswordNotFound() {
        String email = "user@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        // Simular el comportamiento del repositorio para encontrar un usuario por email y contraseña
        when(userRepository.findByEmailAndPassword("email", password)).thenReturn(Optional.of(user));

        assertThrows(NotFoundException.class, () -> userService.findByEmailAndPassword(email, password));
    }

    @Test
    public void testUpdateLoginDate() {
        UUID userId = dataUserTest().getId();
        LocalDateTime originalLastLogin = LocalDateTime.now().minusDays(1);
        User user = dataUserTest();

        // Simular el comportamiento del repositorio para actualizar la fecha de último inicio de sesión
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.updateLoginDate(user);

        // Verificar que la fecha de último inicio de sesión se haya actualizado
        assertNotEquals(originalLastLogin, user.getLastLogin());
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
            "generated_token",
            Boolean.TRUE,
            List.of(PhoneMapper.toEntity(PhoneRequestDTO.builder()
                .contrycode("57")
                .citycode("1")
                .number("1234567")
                .build()))
        );
    }

    private UserRequestDTO dataUserRequestTest() {
        return UserRequestDTO.builder()
            .name("pruebas tests")
            .email("pruebas@test.com")
            .password("password")
            .phones(List.of())
            .build();
    }

}