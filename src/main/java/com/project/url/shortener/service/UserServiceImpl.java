package com.project.url.shortener.service;

import com.project.url.shortener.dao.UserDao;
import com.project.url.shortener.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public Boolean isUserExists(String email) {
        return userDao.isUserExists(email);
    }

    @Override
    public void updateUserPassword(User user) {
        userDao.updateUserPassword(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
