package com.project.url.shortener.rest;

import com.project.url.shortener.commons.request.ShoretenUrlRequest;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.exception.UserNotFoundException;
import com.project.url.shortener.service.ShortenUrlService;
import com.project.url.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/url")
public class ShortenUrlUserController {

    @Autowired
    private ShortenUrlService urlService;

    @Autowired
    private UserService userService;

    @PostMapping("/shorten")
    public ShortenUrl shortenUrl(@RequestBody ShoretenUrlRequest request) {
        checkUserExists(request.getUserEmail());
        return urlService.persistShortenUrl(request.getDestinationUrl(), request.getUserEmail());
    }

    @GetMapping("/list")
    public List<ShortenUrl> getAll(@RequestParam String userEmail) {
        checkUserExists(userEmail);
        return urlService.getAllShortenUrlsByUser(userEmail);
    }

    private void checkUserExists(String userEmail){
        if(!userService.isUserExists(userEmail)){
            throw new UserNotFoundException(String.format("Email - %s is not registered", userEmail));
        }
    }
}
