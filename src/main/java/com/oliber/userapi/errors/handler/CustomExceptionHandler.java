package com.oliber.userapi.errors.handler;

import com.oliber.userapi.dto.response.ExceptionErrorResponseDTO;
import com.oliber.userapi.errors.exception.EmailAlreadyExistsException;
import com.oliber.userapi.errors.exception.LoginException;
import com.oliber.userapi.errors.exception.NotFoundException;
import com.oliber.userapi.errors.exception.UnauthorizedException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionErrorResponseDTO> handleRuntimeException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionErrorResponseDTO("Error internto del servidor intentelo mas tarde..."));
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionErrorResponseDTO> handleUnauthorizedException(final UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ExceptionErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionErrorResponseDTO> handleAccessDeniedException(final AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ExceptionErrorResponseDTO("No tiene permisos para acceder a este recurso."));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionErrorResponseDTO> handleNotFoundException(final NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ExceptionErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionErrorResponseDTO> handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errors = Arrays.stream(Objects.requireNonNull(ex.getDetailMessageArguments()))
            .map(Object::toString)
            .filter(s -> !s.equals("[]"))
            .collect(Collectors.joining(", "));
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionErrorResponseDTO(errors));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionErrorResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionErrorResponseDTO> handleLoginException(LoginException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ExceptionErrorResponseDTO(ex.getMessage()));
    }
}
