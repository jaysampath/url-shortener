package com.project.url.shortener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.url.shortener.dao.ShortenUrlDao;
import com.project.url.shortener.dao.UserDao;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.rest.request.LoginRequest;
import com.project.url.shortener.rest.request.ShoretenUrlRequest;
import com.project.url.shortener.rest.request.SignupRequest;
import com.project.url.shortener.rest.response.ShortenUrlResponse;
import com.project.url.shortener.rest.response.SuccessfulLoginResponse;
import com.project.url.shortener.service.ShortenUrlService;
import com.project.url.shortener.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortenUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortenUrlService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    ShortenUrlDao shortenUrlDao;

    private final String TEST_USER_EMAIL1 = "user1@test.com";
    private final String TEST_USER_EMAIL2 = "user2@test.com";
    private final String TEST_USER_PASSWORD1 = "password1";
    private final String TEST_USER_PASSWORD2 = "password2";
    private final String TEST_USER_USERNAME1 = "username1";
    private final String TEST_USER_USERNAME2 = "username2";

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER = "Bearer ";

    private final String DESTINATION_URL = "https://github.com/jaysampath";
    private final String ALIAS = "my-github";

    private final String SERVER_ADDRESS = "http://localhost:8080/";

    private String accessTokenUser1;
    private String accessTokenUser2;

    @Before
    public void setup() throws Exception {
        clearTestProxiesAndUsers();
        registerTestUsers();
        authenticateTestUsers();
    }


    private void registerTestUsers() throws Exception {
        //register user1
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(TEST_USER_EMAIL1);
        signupRequest.setPassword(TEST_USER_PASSWORD1);
        signupRequest.setUsername(TEST_USER_USERNAME1);
        mockMvc.perform(post(Endpoints.USER_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk());

        //register user 2
        signupRequest.setEmail(TEST_USER_EMAIL2);
        signupRequest.setPassword(TEST_USER_PASSWORD2);
        signupRequest.setUsername(TEST_USER_USERNAME2);

        mockMvc.perform(post(Endpoints.USER_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk());
    }

    public void authenticateTestUsers() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(TEST_USER_EMAIL1);
        loginRequest.setPassword(TEST_USER_PASSWORD1);

        MvcResult result = mockMvc.perform(post(Endpoints.USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        SuccessfulLoginResponse loginResponse = objectMapper.readValue(responseString, SuccessfulLoginResponse.class);
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getAccessToken());
        accessTokenUser1 = BEARER + loginResponse.getAccessToken();

        loginRequest.setEmail(TEST_USER_EMAIL2);
        loginRequest.setPassword(TEST_USER_PASSWORD2);

        result = mockMvc.perform(post(Endpoints.USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        loginResponse = objectMapper.readValue(responseString, SuccessfulLoginResponse.class);
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getAccessToken());
        accessTokenUser2 = BEARER + loginResponse.getAccessToken();

    }

    @Test
    public void testInvalidUrl() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl("https://some..com");
        requestBody.setUserEmail(TEST_USER_EMAIL1);
        requestBody.setAlias("");
        requestBody.setIsAlias(false);


        mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testInvalidSchema() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl("www://something.com");
        requestBody.setUserEmail(TEST_USER_EMAIL1);
        requestBody.setAlias("");
        requestBody.setIsAlias(false);


        mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testShortenValidUrl() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl(DESTINATION_URL);
        requestBody.setUserEmail(TEST_USER_EMAIL1);
        requestBody.setAlias("");
        requestBody.setIsAlias(false);

        MvcResult result = mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ShortenUrlResponse persistedUrl = objectMapper.readValue(responseString, ShortenUrlResponse.class);
        assertNotNull(persistedUrl);
        assertNotNull(persistedUrl.getProxy());
        assertFalse(persistedUrl.getIsAlias());
        assertEquals(SERVER_ADDRESS.length() + 8, persistedUrl.getProxy().length());

        //duplicate entry
        result = mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        ShortenUrlResponse newUrl = objectMapper.readValue(responseString, ShortenUrlResponse.class);
        assertNotNull(newUrl);
        assertEquals(persistedUrl.getProxy(), newUrl.getProxy());
        assertEquals(persistedUrl.getDestinationUrl(), newUrl.getDestinationUrl());
        assertEquals(persistedUrl.getIsAlias(), newUrl.getIsAlias());
        assertEquals(persistedUrl.getUserEmail(), newUrl.getUserEmail());

        // same destination url from different user
        requestBody.setUserEmail(TEST_USER_EMAIL2);
        result = mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser2)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();

        responseString = result.getResponse().getContentAsString();
        ShortenUrlResponse duplicateDestination = objectMapper.readValue(responseString, ShortenUrlResponse.class);
        assertNotNull(duplicateDestination);
        assertEquals(TEST_USER_EMAIL2, duplicateDestination.getUserEmail());
        assertNotEquals(persistedUrl.getProxy(), duplicateDestination.getProxy());
        assertEquals(persistedUrl.getDestinationUrl(), duplicateDestination.getDestinationUrl());
        assertEquals(persistedUrl.getIsAlias(), duplicateDestination.getIsAlias());
        assertNotEquals(persistedUrl.getUserEmail(), duplicateDestination.getUserEmail());


        // list all user1 urls test
        result = mockMvc.perform(get(Endpoints.LIST_USER_URLS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .param("userEmail", TEST_USER_EMAIL1)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        List<ShortenUrlResponse> urlList = objectMapper.readValue(responseString, new TypeReference<List<ShortenUrlResponse>>() {
        });
        assertEquals(1, urlList.size());
        assertEquals(persistedUrl.getProxy(), urlList.get(0).getProxy());

        // list all user2 urls test
        result = mockMvc.perform(get(Endpoints.LIST_USER_URLS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .param("userEmail", TEST_USER_EMAIL2)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        urlList = objectMapper.readValue(responseString, new TypeReference<List<ShortenUrlResponse>>() {
        });
        assertEquals(1, urlList.size());
        assertEquals(duplicateDestination.getProxy(), urlList.get(0).getProxy());

    }

    @Test
    public void testAlias() throws Exception {
        ShoretenUrlRequest requestBody = new ShoretenUrlRequest();
        requestBody.setDestinationUrl(DESTINATION_URL);
        requestBody.setUserEmail(TEST_USER_EMAIL1);
        requestBody.setAlias(ALIAS);
        requestBody.setIsAlias(true);

        MvcResult result = mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ShortenUrlResponse persistedUrl = objectMapper.readValue(responseString, ShortenUrlResponse.class);
        assertNotNull(persistedUrl);
        assertNotNull(persistedUrl.getProxy());
        assertTrue(persistedUrl.getIsAlias());
        assertEquals(persistedUrl.getProxy(), SERVER_ADDRESS + ALIAS);

        //duplicate entry
        mockMvc.perform(post(Endpoints.SHORTEN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotAcceptable());

        // validate already reported alias
        mockMvc.perform(post(Endpoints.VALIDATE_ALIAS)
                .header(AUTHORIZATION_HEADER, accessTokenUser1)
                .param("alias", ALIAS))
                .andExpect(status().isAlreadyReported());

        // validate fresh alias
        mockMvc.perform(post(Endpoints.VALIDATE_ALIAS)
                        .header(AUTHORIZATION_HEADER, accessTokenUser1)
                        .param("alias", "fresh"))
                .andExpect(status().isOk());

    }

    @Test
    public void testIncorrectProxy() throws Exception {
        mockMvc.perform(get("/{id}", "wrong"))
                .andExpect(status().isNotFound());
    }

    private void clearTestProxiesAndUsers(){
        shortenUrlDao.deleteProxiesByUserEmail(TEST_USER_EMAIL1);
        shortenUrlDao.deleteProxiesByUserEmail(TEST_USER_EMAIL2);

        userDao.deleteUser(TEST_USER_EMAIL1);
        userDao.deleteUser(TEST_USER_EMAIL2);
    }
}
