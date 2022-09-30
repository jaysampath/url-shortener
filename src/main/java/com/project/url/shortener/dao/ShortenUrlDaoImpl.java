package com.project.url.shortener.dao;

import com.project.url.shortener.commons.ShortenUrlUtils;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.repository.ShortenUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class ShortenUrlDaoImpl implements ShortenUrlDao{

    Logger logger = LoggerFactory.getLogger(ShortenUrlDaoImpl.class);

    @Autowired
    private ShortenUrlRepository repository;

    @Autowired
    ShortenUrlUtils shortenUrlUtils;


    @Override
    public ShortenUrl getShortenUrl(String destinationUrl) {
        return repository.findByDestinationUrl(destinationUrl);
    }

    @Override
    public ShortenUrl persistShortenUrl(String destinationUrl, String userEmail) {
         ShortenUrl savedUrl = this.getShortenUrl(destinationUrl);
         if(savedUrl != null){
             logger.info("A proxy corresponding to destinationUrl is already found - {}", savedUrl);
             return savedUrl;
         }

         ShortenUrl newUrl = new ShortenUrl();
         newUrl.setDestinationUrl(destinationUrl);
         newUrl.setProxy(shortenUrlUtils.getShortenUrl(destinationUrl));
         newUrl.setUserEmail(userEmail);

         ShortenUrl persistedUrl = repository.save(newUrl);
         logger.info("Persisted url - {} ", persistedUrl);
         return persistedUrl;
    }

    @Override
    public String getDestinationUrl(String proxy) {
        Optional<ShortenUrl> shortenUrl = repository.findById(proxy);
        return shortenUrl.isPresent() ? shortenUrl.get().getDestinationUrl() : null;
    }

    @Override
    public List<ShortenUrl> getAllShortenUrls() {
        return repository.findAll();
    }

    @Override
    public List<ShortenUrl> getAllShortenUrlsByUser(String email) {
        return repository.findAllByUserEmail(email);
    }
}
