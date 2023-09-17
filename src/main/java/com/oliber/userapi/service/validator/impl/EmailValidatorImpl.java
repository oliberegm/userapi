package com.oliber.userapi.service.validator.impl;

import com.oliber.userapi.service.configuration.IMaintenanceService;
import com.oliber.userapi.service.validator.EmailValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {

    private final IMaintenanceService maintenanceService;


    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        return isValidEmail(field);
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(maintenanceService.getRegexEmail());
    }
}
