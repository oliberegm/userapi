package com.oliber.userapi.service.configuration.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaintenanceServiceTest {
    private MaintenanceService maintenanceService;

    @BeforeEach
    public void setUp() {
        maintenanceService = new MaintenanceService();
    }

    @Test
    void testGetAndSetRegexEmail() {
        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

        maintenanceService.setRegexEmail(regexEmail);
        String retrievedRegex = maintenanceService.getRegexEmail();

        assertEquals(regexEmail, retrievedRegex);
    }

    @Test
    void testGetAndSetRegexPassword() {
        String regexPassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";

        maintenanceService.setRegexPassword(regexPassword);
        String retrievedRegex = maintenanceService.getRegexPassword();

        assertEquals(regexPassword, retrievedRegex);
    }
}