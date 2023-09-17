package com.oliber.userapi.docs;

import com.oliber.userapi.dto.request.LoginRequestDTO;
import com.oliber.userapi.dto.response.ExceptionErrorResponseDTO;
import com.oliber.userapi.dto.response.MessageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface LoginApiDoc {

    @Operation(summary = "Validación de usuario", tags = {"RestApi Login"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado se retorna el token",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "datos ingresados invalidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<MessageResponseDTO> login(@Parameter(description = "DTO datos de login de usuario") LoginRequestDTO loginRequest);
}
