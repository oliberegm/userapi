package com.oliber.userapi.service.security.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.oliber.userapi.errors.exception.LoginException;
import com.oliber.userapi.errors.exception.NotFoundException;
import com.oliber.userapi.model.User;
import com.oliber.userapi.service.user.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginServiceTest {
    @Mock
    private UserService userService;

    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginService = new LoginService(userService);
    }

    @Test
    void testLoginSuccessful() {
        String email = "user@example.com";
        String password = "password";
        String expectedToken = "generated_token";

        // Simular el comportamiento del userService para un inicio de sesión exitoso
        User user = new User();
        user.setToken(expectedToken);
        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);

        String resultToken = loginService.login(email, password);

        assertEquals(expectedToken, resultToken);
        // Verificar que el método updateLoginDate se haya llamado una vez
        verify(userService, times(1)).updateLoginDate(user);
    }

    @Test
    void testLoginInvalidCredentials() {
        String email = "invalid@example.com";
        String password = "invalid_password";

        // Simular el comportamiento del userService para un inicio de sesión con credenciales inválidas
        when(userService.findByEmailAndPassword(email, password)).thenThrow(new NotFoundException("Usuario no encontrado"));

        // Verificar que se lanza una excepción LoginException
        assertThrows(LoginException.class, () -> loginService.login(email, password));
    }
}