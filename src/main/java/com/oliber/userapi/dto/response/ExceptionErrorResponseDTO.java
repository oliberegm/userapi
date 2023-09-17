package com.oliber.userapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionErrorResponseDTO {
    private final String message;
}
