package com.oliber.userapi.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {
    private final String id;
    private final LocalDateTime created;
    private final LocalDateTime modified;
    private final LocalDateTime lastLogin;
    private final String token;
    private final Boolean isActive;
}
