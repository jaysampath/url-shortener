package com.project.url.shortener.dao;

import com.project.url.shortener.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserByEmail(String email);

    void saveUser(User user);

    Boolean isUserExists(String email);

    void updateUserPassword(User user);

}
