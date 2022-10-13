package com.project.url.shortener.service;

import com.project.url.shortener.entity.ShortenUrl;

import java.util.List;

public interface ShortenUrlService {

    ShortenUrl persistShortenUrl(String destinationUrl, String userEmail, Boolean isAlias, String alias);

    String getDestinationUrl(String proxy);

    List<ShortenUrl> getAllShortenUrls();

    List<ShortenUrl> getAllShortenUrlsByUser(String email);

    boolean checkIfAliasAlreadyTaken(String alias);
}
