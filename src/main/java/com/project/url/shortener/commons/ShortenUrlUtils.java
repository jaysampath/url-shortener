package com.project.url.shortener.commons;

import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ShortenUrlUtils {

    @Value("${short.url.length}")
    private int shortenUrlLength;

    @Autowired
    private Base62 base62;

    public String getShortenUrl(String destinationUrl){
        String md5Hash = MD5Utils.getMd5Hash(destinationUrl);
        byte[] encoded = base62.encode(md5Hash.getBytes(StandardCharsets.UTF_8));
        String encodedString = new String(encoded);
        return encodedString.substring(0, shortenUrlLength);
    }
}
