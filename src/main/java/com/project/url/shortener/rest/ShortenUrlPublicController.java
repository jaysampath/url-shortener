package com.project.url.shortener.rest;

import com.project.url.shortener.exception.ProxyNotFoundException;
import com.project.url.shortener.service.ShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ShortenUrlPublicController {

    @Autowired
    private ShortenUrlService urlService;

    @GetMapping("/{proxy}")
    public ResponseEntity getDestinationUrl(HttpServletResponse response, @PathVariable String proxy) throws IOException {
        String destinationUrl = urlService.getDestinationUrl(proxy);

        if (destinationUrl == null) {
            throw new ProxyNotFoundException(String.format("Proxy - %s not found", proxy));
        }

        response.sendRedirect(destinationUrl);
        return ResponseEntity.ok().build();
    }
}
