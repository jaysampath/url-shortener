package com.project.url.shortener.service;

import com.project.url.shortener.entity.User;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    void saveUser(User user);

    Boolean isUserExists(String email);

    void updateUserPassword(User user);

    List<User> getUsers();
}
