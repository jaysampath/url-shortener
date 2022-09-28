package com.project.url.shortener;

import com.project.url.shortener.commons.MD5Utils;
import com.project.url.shortener.commons.ShortenUrlUtils;
import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class URLShortenTest {

    @Autowired
    private ShortenUrlUtils shortenUrlUtils;

    @Test
    public void test(){
        String url = "https://leetcode.com/jayasampath/";
        String proxy = shortenUrlUtils.getShortenUrl(url);

        assertEquals(8, proxy.length());
    }

}
