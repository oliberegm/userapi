package com.oliber.userapi.mapper;

import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.dto.response.UserResponseDTO;
import com.oliber.userapi.model.User;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
            .id(user.getId().toString())
            .created(user.getCreated())
            .isActive(user.getIsActive())
            .lastLogin(user.getLastLogin())
            .modified(user.getModified())
            .token(user.getToken())
            .build();
    }

    public static User toEntity(UserRequestDTO userRequestDTO) {
        return new User(
            null,
            userRequestDTO.getName(),
            userRequestDTO.getEmail(),
            userRequestDTO.getPassword(),
            null,
            null,
            null,
            null,
            null,
            userRequestDTO.getPhones().stream().map(PhoneMapper::toEntity).toList()
        );
    }
}
