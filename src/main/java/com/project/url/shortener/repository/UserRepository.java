package com.project.url.shortener.repository;

import com.project.url.shortener.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    void deleteByEmail(String email);
}
