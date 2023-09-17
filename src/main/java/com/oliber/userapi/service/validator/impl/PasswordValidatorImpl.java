package com.oliber.userapi.service.validator.impl;

import com.oliber.userapi.service.configuration.IMaintenanceService;
import com.oliber.userapi.service.validator.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {
    private final IMaintenanceService maintenanceService;

    public PasswordValidatorImpl(IMaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        return isValidPassword(field);
    }

    private boolean isValidPassword(String pass) {
        if (pass == null || pass.isEmpty()) {
            return false;
        }
        return pass.matches(maintenanceService.getRegexPassword());
    }
}
