package com.oliber.userapi.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRequestDTO {
    private final String email;
    private final String password;
}
