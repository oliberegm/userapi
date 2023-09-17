package com.oliber.userapi.service.configuration.impl;

import com.oliber.userapi.service.configuration.IMaintenanceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService implements IMaintenanceService {

    @Value("${app.user.email.regex}")
    private String regexEmail;

    @Value("${app.user.password.regex.regexp}")
    private String regexPassword;


    @Override
    public void setRegexEmail(String regexEmail) {
        this.regexEmail = regexEmail;
    }

    @Override
    public String getRegexEmail() {
        return regexEmail;
    }

    @Override
    public void setRegexPassword(String regexPassword) {
        this.regexPassword = regexPassword;
    }

    @Override
    public String getRegexPassword() {
        return regexPassword;
    }
}
