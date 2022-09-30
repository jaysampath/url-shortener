package com.project.url.shortener.rest;

import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.entity.User;
import com.project.url.shortener.service.ShortenUrlService;
import com.project.url.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShortenUrlService shortenUrlService;

    @GetMapping("/list/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/list/proxy")
    public List<ShortenUrl> getAllProxies(){
        return shortenUrlService.getAllShortenUrls();
    }
}
