package com.project.url.shortener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.url.shortener.commons.request.ShoretenUrlRequest;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.service.ShortenUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ShortenUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortenUrlService service;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testInvalidUrl() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl("https://some..com");

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                        .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testInvalidSchema() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl("www://something.com");

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testShortenUrl() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl("https://www.linkedin.com/in/jaya-sampath-kolisetty/");

        MvcResult result = mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();

       String responseString =  result.getResponse().getContentAsString();
       ShortenUrl persistedUrl = objectMapper.readValue(responseString, ShortenUrl.class);
       assertNotNull(persistedUrl);
       assertNotNull(persistedUrl.getProxy());
       assertEquals(8, persistedUrl.getProxy().length());

       //duplicate entry
         result = mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();

        responseString =  result.getResponse().getContentAsString();
        ShortenUrl newUrl = objectMapper.readValue(responseString, ShortenUrl.class);
        assertNotNull(newUrl);
        assertEquals(persistedUrl.getProxy(), newUrl.getProxy());
        assertEquals(persistedUrl.getDestinationUrl(), persistedUrl.getDestinationUrl());


        //Incorrect proxy
        mockMvc.perform(get("/{id}", "wrong"))
                .andExpect(status().isNotFound());

        // list all test
        result = mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andReturn();
        responseString =  result.getResponse().getContentAsString();
        List<ShortenUrl> urlList = objectMapper.readValue(responseString, new TypeReference<List<ShortenUrl>>(){});
        assertNotNull(urlList);
        assertEquals(1, urlList.size());
        assertEquals(persistedUrl.getProxy(), urlList.get(0).getProxy());


        //get destination url
        String destination = service.getDestinationUrl(persistedUrl.getProxy());
        assertEquals(requestBody.getDestinationUrl(), destination);
    }
}
