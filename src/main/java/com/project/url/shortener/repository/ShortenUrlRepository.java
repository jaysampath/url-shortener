package com.project.url.shortener.repository;

import com.project.url.shortener.entity.ShortenUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenUrlRepository extends JpaRepository<ShortenUrl, String> {

    ShortenUrl findByDestinationUrl(String destinationUrl);
}
