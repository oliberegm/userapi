package com.oliber.userapi.docs;

import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.dto.response.ExceptionErrorResponseDTO;
import com.oliber.userapi.dto.response.MessageResponseDTO;
import com.oliber.userapi.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UserApiDoc {

    @Operation(summary = "Obtener todos los usuarios", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrado",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema( schema = @Schema(implementation = UserResponseDTO.class))))
    })
    ResponseEntity<List<UserResponseDTO>> getAllUsers();

    @Operation(summary = "Obtener usuario por id", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "id invalido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<UserResponseDTO> getUserById(@Parameter(description = "identificador del usuario") String id);

    @Operation(summary = "Obtener usuario por email", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "email invalido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<UserResponseDTO> getUserByEmail(String email);

    @Operation(summary = "Creación de usuario", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "datos ingresados invalidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<UserResponseDTO> createUser(@Parameter(description = "DTO datos usuario") UserRequestDTO userRequest);

    @Operation(summary = "Actualizar datos de usuario", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "datos invalido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<UserResponseDTO> updateUser(@Parameter(description = "identificador del usuario") String id,
                                               @Parameter(description = "DTO datos usuario") UserRequestDTO userRequest);

    @Operation(summary = "eliminar usuario", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "usuario eliminado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "id invalido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<MessageResponseDTO> deleteUser(@Parameter(description = "identificador del usuario") String id);


    @Operation(summary = "Activar usuario", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "usuario actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "id invalido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<MessageResponseDTO> activateUser(@Parameter(description = "identificador del usuario") String id);

    @Operation(summary = "Desactivar usuario", tags = {"RestApi Users"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "usuario actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "id invalido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionErrorResponseDTO.class)))
    })
    ResponseEntity<MessageResponseDTO> deactivateUser(@Parameter(description = "identificador del usuario") String id);
}
