package com.oliber.userapi.service.validator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oliber.userapi.service.configuration.IMaintenanceService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class PasswordValidatorImplTest {
    @Mock
    private IMaintenanceService maintenanceService;

    private PasswordValidatorImpl passwordValidator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordValidator = new PasswordValidatorImpl(maintenanceService);
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    void testValidPassword() {
        // Configura el comportamiento del servicio para devolver el patrón de contraseña correcto
        Mockito.when(maintenanceService.getRegexPassword()).thenReturn("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$");

        assertTrue(passwordValidator.isValid("Passw0rd", constraintValidatorContext));
    }

    @Test
    void testInvalidPassword() {
        // Configura el comportamiento del servicio para devolver un patrón de contraseña incorrecto
        Mockito.when(maintenanceService.getRegexPassword()).thenReturn("^(?=.*[a-z])(?=.*\\d).{6,}$");

        assertFalse(passwordValidator.isValid("Weak1", constraintValidatorContext));
    }

    @Test
    void testNullPassword() {
        assertFalse(passwordValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    void testEmptyPassword() {
        assertFalse(passwordValidator.isValid("", constraintValidatorContext));
    }
}