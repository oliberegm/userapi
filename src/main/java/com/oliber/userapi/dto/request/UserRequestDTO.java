package com.oliber.userapi.dto.request;

import com.oliber.userapi.service.validator.EmailValidator;
import com.oliber.userapi.service.validator.PasswordValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequestDTO {
    @NotNull(message = "required name.")
    @NotBlank(message = "required name.")
    private final String name;

    @EmailValidator
    @NotNull(message = "required email.")
    @NotBlank(message = "required email.")
    private final String email;

    @PasswordValidator
    @NotNull(message = "required password.")
    @NotBlank(message = "required password.")
    private final String password;

    @NotEmpty(message = "required at least one phone.")
    private final List<PhoneRequestDTO> phones;
}
