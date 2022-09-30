package com.project.url.shortener.bootstrap;

import com.project.url.shortener.entity.User;
import com.project.url.shortener.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateAdminUser implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(CreateAdminUser.class);

    private static final String ADMIN_EMAIL = "admin@urlshortener.com";
    private static final String ADMIN_PASSWORD = "password";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(!userService.getUsers().isEmpty()){
            return;
        }

        User adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        adminUser.setUsername("Admin Admin");
        adminUser.setRole("ADMIN");

        userService.saveUser(adminUser);
        logger.info("App successfully bootstrapped.");
    }
}
