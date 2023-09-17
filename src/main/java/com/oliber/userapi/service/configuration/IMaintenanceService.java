package com.oliber.userapi.service.configuration;

public interface IMaintenanceService {
    void setRegexEmail(String regexEmail);

    String getRegexEmail();

    void setRegexPassword(String regexPassword);

    String getRegexPassword();
}
