package com.project.url.shortener.dao;

import com.project.url.shortener.entity.ShortenUrl;

import java.util.List;

public interface ShortenUrlDao {

    ShortenUrl getShortenUrl(String destinationUrl);

    ShortenUrl persistShortenUrl(String destination);

    String getDestinationUrl(String proxy);

    List<ShortenUrl> getAllShortenUrls();
}
