package com.project.url.shortener.rest;

import com.project.url.shortener.commons.request.ShoretenUrlRequest;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.exception.ProxyNotFoundException;
import com.project.url.shortener.service.ShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ShortenUrlRestController {

    @Autowired
    private ShortenUrlService urlService;

    @GetMapping("/{proxy}")
    public ResponseEntity tionUrl(HttpServletResponse response, @PathVariable String proxy) throws IOException {
        String destinationUrl = urlService.getDestinationUrl(proxy);

        if (destinationUrl == null) {
            throw new ProxyNotFoundException(String.format("Proxy - %s not found", proxy));
        }

        response.sendRedirect(destinationUrl);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shorten")
    public ShortenUrl shortenUrl(@RequestBody ShoretenUrlRequest request) {
        return urlService.persistShortenUrl(request.getDestinationUrl());
    }

    @GetMapping("/list")
    public List<ShortenUrl> getAll() {
        return urlService.getAllShortenUrls();
    }
}
