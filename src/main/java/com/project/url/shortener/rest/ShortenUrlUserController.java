package com.project.url.shortener.rest;

import com.project.url.shortener.exception.InvalidInputException;
import com.project.url.shortener.rest.request.ShoretenUrlRequest;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.exception.UserNotFoundException;
import com.project.url.shortener.service.ShortenUrlService;
import com.project.url.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ShortenUrlUserController {

    @Autowired
    private ShortenUrlService urlService;

    @Autowired
    private UserService userService;

    @PostMapping("/shorten")
    public ShortenUrl shortenUrl(@RequestBody ShoretenUrlRequest request) {
        checkUserExists(request.getUserEmail());
        if(request.getIsAlias() && !StringUtils.hasText(request.getAlias())){
            throw new InvalidInputException("Alias is empty!!");
        }
        return urlService.persistShortenUrl(request.getDestinationUrl(), request.getUserEmail(), request.getIsAlias(), request.getAlias());
    }

    @PostMapping("/validate/alias")
    public ResponseEntity checkAliasCanBeTaken(@RequestParam String alias){
        if(urlService.checkIfAliasAlreadyTaken(alias)){
            return ResponseEntity.status(208).build();
        }else{
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/urls/list")
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
