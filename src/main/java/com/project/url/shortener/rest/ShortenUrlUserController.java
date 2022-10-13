package com.project.url.shortener.rest;

import com.project.url.shortener.exception.InvalidInputException;
import com.project.url.shortener.rest.request.ShoretenUrlRequest;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.exception.UserNotFoundException;
import com.project.url.shortener.rest.response.ShortenUrlResponse;
import com.project.url.shortener.service.ShortenUrlService;
import com.project.url.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class ShortenUrlUserController {

    private static final String SERVER_URL = "http://localhost:8080/";

    @Autowired
    private ShortenUrlService urlService;

    @Autowired
    private UserService userService;

    @PostMapping("/shorten")
    public ShortenUrlResponse shortenUrl(@RequestBody ShoretenUrlRequest request) {
        checkUserExists(request.getUserEmail());
        if(request.getIsAlias() && !StringUtils.hasText(request.getAlias())){
            throw new InvalidInputException("Alias is empty!!");
        }
        ShortenUrl persistedUrl = urlService.persistShortenUrl(request.getDestinationUrl(),
                request.getUserEmail(), request.getIsAlias(), request.getAlias());

        return createShortenUrlObject(persistedUrl.getProxy(), persistedUrl.getDestinationUrl(),
                persistedUrl.getUserEmail(), persistedUrl.getIsAlias());
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
    public List<ShortenUrlResponse> getAll(@RequestParam String userEmail) {
        checkUserExists(userEmail);
        return mapShortenUrlsToResponse(urlService.getAllShortenUrlsByUser(userEmail));
    }

    private void checkUserExists(String userEmail){
        if(!userService.isUserExists(userEmail)){
            throw new UserNotFoundException(String.format("Email - %s is not registered", userEmail));
        }
    }

    private List<ShortenUrlResponse> mapShortenUrlsToResponse(List<ShortenUrl> urls){
        return urls.stream()
                .map(shortenUrl -> createShortenUrlObject(shortenUrl.getProxy(),
                shortenUrl.getDestinationUrl(), shortenUrl.getUserEmail(), shortenUrl.getIsAlias()))
                .collect(Collectors.toList());
    }

    private ShortenUrlResponse createShortenUrlObject(String proxy, String destinationUrl, String userEmail, Boolean isAlias) {
       ShortenUrlResponse response = new ShortenUrlResponse();
        response.setProxy(SERVER_URL + proxy);
        response.setDestinationUrl(destinationUrl);
        response.setIsAlias(isAlias);
        response.setUserEmail(userEmail);
        return response;
    }

}
