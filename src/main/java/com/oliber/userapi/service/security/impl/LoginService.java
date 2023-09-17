package com.oliber.userapi.service.security.impl;

import com.oliber.userapi.errors.exception.LoginException;
import com.oliber.userapi.errors.exception.NotFoundException;
import com.oliber.userapi.model.User;
import com.oliber.userapi.service.security.ILoginService;
import com.oliber.userapi.service.user.impl.UserService;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String login(String email, String password) {
        try {
            User user = userService.findByEmailAndPassword(email, password);
            userService.updateLoginDate(user);
            return user.getToken();
        } catch (NotFoundException e) {
            throw new LoginException("Usuario y/o contraseña incorrectos");
        }
    }
}
