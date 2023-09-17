package com.oliber.userapi.controller;

import com.oliber.userapi.docs.LoginApiDoc;
import com.oliber.userapi.dto.request.LoginRequestDTO;
import com.oliber.userapi.dto.response.MessageResponseDTO;
import com.oliber.userapi.service.security.ILoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginApiDoc {

    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String token = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(MessageResponseDTO.builder().message(token).build());
    }
}
