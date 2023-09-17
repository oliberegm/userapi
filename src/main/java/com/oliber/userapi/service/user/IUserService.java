package com.oliber.userapi.service.user;

import com.oliber.userapi.dto.request.UserRequestDTO;
import com.oliber.userapi.model.User;
import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(String id);

    User getUserByEmail(String email);

    User createUser(UserRequestDTO userRequest);

    User updateUser(String id, User userRequest);

    void deleteUser(String id);

    void activateUser(String id);

    void deactivateUser(String id);


    User findByEmailAndPassword(String email, String password);

    void updateLoginDate(User user);
}
