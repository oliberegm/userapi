package com.oliber.userapi.service.security;

public interface ITokenService {
    String generateToken(String email);
}
