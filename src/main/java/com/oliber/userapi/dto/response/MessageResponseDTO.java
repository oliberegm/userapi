package com.oliber.userapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponseDTO {
    private final String message;
}
