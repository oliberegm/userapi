package com.oliber.userapi.service.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import com.oliber.userapi.service.validator.impl.PasswordValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidatorImpl.class)
public @interface PasswordValidator {
    String message() default "Format password invalid...";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
