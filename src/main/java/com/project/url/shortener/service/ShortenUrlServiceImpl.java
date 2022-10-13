package com.project.url.shortener.service;

import com.project.url.shortener.dao.ShortenUrlDao;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.exception.AliasAlreadyTakenException;
import com.project.url.shortener.exception.InvalidSchemaException;
import com.project.url.shortener.exception.InvalidUrlException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortenUrlServiceImpl implements ShortenUrlService {

    @Autowired
    private ShortenUrlDao dao;

    @Autowired
    private UrlValidator urlValidator;

    @Override
    public ShortenUrl persistShortenUrl(String destination, String userEmail, Boolean isAlias, String alias) {

        if(!validateSchema(destination)){
            throw new InvalidSchemaException("Invalid Schema. Allowed Schemas - http, https");
        }

        if(!urlValidator.isValid(destination)){
            throw new InvalidUrlException("Invalid Url - "+ destination);
        }

        if(isAlias && this.checkIfAliasAlreadyTaken(alias)){
            throw new AliasAlreadyTakenException("Alias is already taken. Please choose another alias");
        }

        return dao.persistShortenUrl(destination, userEmail, isAlias, alias);
    }

    @Override
    public String getDestinationUrl(String proxy) {
        return dao.getDestinationUrl(proxy);
    }

    @Override
    public List<ShortenUrl> getAllShortenUrls() {
        return dao.getAllShortenUrls();
    }

    @Override
    public List<ShortenUrl> getAllShortenUrlsByUser(String email) {
        return dao.getAllShortenUrlsByUser(email);
    }

    @Override
    public boolean checkIfAliasAlreadyTaken(String alias) {
        return dao.checkIfAliasAlreadyTaken(alias);
    }

    private boolean validateSchema(String destination) {
        return ((destination.length() > 5) && (destination.startsWith("http") || destination.startsWith("https")));
    }

}
