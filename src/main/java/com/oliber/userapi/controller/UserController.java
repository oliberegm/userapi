package com.oliber.userapi.controller;

import com.oliber.userapi.docs.UserApiDoc;
import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.dto.response.MessageResponseDTO;
import com.oliber.userapi.dto.response.UserResponseDTO;
import com.oliber.userapi.mapper.UserMapper;
import com.oliber.userapi.service.user.IUserService;
import com.oliber.userapi.service.validator.EmailValidator;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController implements UserApiDoc {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(UserMapper.toDTO(userService.createUser(userRequest)));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(UserMapper::toDTO).toList());
    }


    @GetMapping(value = "/byEmail", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@Valid @EmailValidator @RequestParam String email) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserByEmail(email)));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserById(id)));
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.updateUser(id, UserMapper.toEntity(userRequest))));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(MessageResponseDTO.builder().message("User deleted successfully.").build());
    }

    @PatchMapping(value = "/{id}/activate", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> activateUser(@PathVariable String id) {
        userService.activateUser(id);
        return ResponseEntity.ok(MessageResponseDTO.builder().message("User activated successfully.").build());
    }

    @PatchMapping(value = "/{id}/deactivate", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> deactivateUser(@PathVariable String id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(MessageResponseDTO.builder().message("User deactivated successfully.").build());
    }

}
