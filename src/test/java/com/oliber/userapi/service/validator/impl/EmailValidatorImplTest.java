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

class EmailValidatorImplTest {
    @Mock
    private IMaintenanceService maintenanceService;

    private EmailValidatorImpl emailValidator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        emailValidator = new EmailValidatorImpl(maintenanceService);
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    void testValidEmail() {
        // Configura el comportamiento del servicio para devolver el patrón de correo electrónico correcto
        Mockito.when(maintenanceService.getRegexEmail()).thenReturn("^\\w+@\\w+\\.com$");

        assertTrue(emailValidator.isValid("test@example.com", constraintValidatorContext));
    }

    @Test
    void testInvalidEmail() {
        // Configura el comportamiento del servicio para devolver un patrón de correo electrónico incorrecto
        Mockito.when(maintenanceService.getRegexEmail()).thenReturn("^\\w+@\\w+\\.org$");

        assertFalse(emailValidator.isValid("test@example.com", constraintValidatorContext));
    }

    @Test
    void testNullEmail() {
        assertFalse(emailValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    void testEmptyEmail() {
        assertFalse(emailValidator.isValid("", constraintValidatorContext));
    }
}