package com.oliber.userapi.service.user.impl;

import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.errors.exception.EmailAlreadyExistsException;
import com.oliber.userapi.errors.exception.NotFoundException;
import com.oliber.userapi.mapper.PhoneMapper;
import com.oliber.userapi.model.User;
import com.oliber.userapi.repository.UserRepository;
import com.oliber.userapi.service.security.ITokenService;
import com.oliber.userapi.service.user.IUserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String EMAIL_ALREADY_EXISTS = "El correo ya esta registrado.";

    private final UserRepository userRepository;
    private final ITokenService tokenService;

    public UserService(UserRepository userRepository, ITokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User createUser(UserRequestDTO userRequest) {
        validEmail(userRequest.getEmail());
        User user = new User(
            UUID.randomUUID(),
            userRequest.getName(),
            userRequest.getEmail(),
            userRequest.getPassword(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
            tokenService.generateToken(userRequest.getEmail()),
            Boolean.TRUE,
            userRequest.getPhones().stream().map(PhoneMapper::toEntity).toList()
        );
        return userRepository.save(user);
    }

    private void validEmail(String email) {
        userRepository.findByEmail(email)
            .ifPresent(user -> {
                throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
            });
    }

    @Override
    public User updateUser(String id, User userRequest) {
        User user = this.getUserById(id);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setModified(LocalDateTime.now());
        user.getPhones().addAll(userRequest.getPhones());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public void activateUser(String id) {
        changeStatusUser(id, Boolean.TRUE);
    }

    @Override
    public void deactivateUser(String id) {
        changeStatusUser(id, Boolean.FALSE);
    }

    private void changeStatusUser(String id, Boolean status) {
        User user = this.getUserById(id);
        user.setIsActive(status);
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public void updateLoginDate(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
