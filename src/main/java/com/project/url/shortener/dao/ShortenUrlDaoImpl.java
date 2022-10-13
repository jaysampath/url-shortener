package com.project.url.shortener.dao;

import com.project.url.shortener.commons.ShortenUrlUtils;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.repository.ShortenUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Component
@Transactional
public class ShortenUrlDaoImpl implements ShortenUrlDao {

    Logger logger = LoggerFactory.getLogger(ShortenUrlDaoImpl.class);

    @Autowired
    private ShortenUrlRepository repository;

    @Autowired
    ShortenUrlUtils shortenUrlUtils;

    private Random random = new Random();


    @Override
    public ShortenUrl getShortenUrl(String destinationUrl) {
        return repository.findByDestinationUrl(destinationUrl);
    }

    @Override
    public synchronized ShortenUrl persistShortenUrl(String destinationUrl, String userEmail, Boolean isAlias, String alias) {

        ShortenUrl savedUrl = repository.findByDestinationUrlAndUserEmailAndIsAlias(destinationUrl, userEmail, false);

        if (savedUrl != null) {
            logger.info("A proxy corresponding to the given destinationUrl is already found - {}", savedUrl);
            return savedUrl;
        }

        ShortenUrl newUrl = new ShortenUrl();
        newUrl.setDestinationUrl(destinationUrl);

        //what if two users want to shorten same url ??
        if (isAlias) {
            newUrl.setProxy(alias);
            newUrl.setIsAlias(true);
        } else {
            newUrl.setProxy(shortenUrlUtils.getShortenUrl(destinationUrl + random.nextInt(50)));
            newUrl.setIsAlias(false);
        }

        newUrl.setUserEmail(userEmail);

        ShortenUrl persistedUrl;
        try {
            persistedUrl = repository.save(newUrl);
            logger.info("Persisted url - {} ", persistedUrl);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while persisting!, please try again after sometime");
        }
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

    @Override
    public boolean checkIfAliasAlreadyTaken(String alias) {
        return repository.findById(alias).isPresent();
    }

    @Override
    public void deleteProxiesByUserEmail(String userEmail) {
        repository.deleteAllByUserEmail(userEmail);
    }


}
