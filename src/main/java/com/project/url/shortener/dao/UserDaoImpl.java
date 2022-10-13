package com.project.url.shortener.dao;

import com.project.url.shortener.entity.User;
import com.project.url.shortener.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserDaoImpl implements UserDao{

    @Autowired
    private UserRepository repository;


    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public Boolean isUserExists(String email) {
        return repository.findByEmail(email) != null;
    }

    @Override
    public void updateUserPassword(User user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(String userEmail) {
        repository.deleteByEmail(userEmail);
    }
}
