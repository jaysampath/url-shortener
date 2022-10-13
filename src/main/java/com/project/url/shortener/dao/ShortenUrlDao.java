package com.project.url.shortener.dao;

import com.project.url.shortener.entity.ShortenUrl;

import java.util.List;

public interface ShortenUrlDao {

    ShortenUrl getShortenUrl(String destinationUrl);

    ShortenUrl persistShortenUrl(String destination, String userEmail, Boolean isAlias, String alias);

    String getDestinationUrl(String proxy);

    List<ShortenUrl> getAllShortenUrls();

    List<ShortenUrl> getAllShortenUrlsByUser(String email);

    boolean checkIfAliasAlreadyTaken(String alias);

    void deleteProxiesByUserEmail(String userEmail);
}
