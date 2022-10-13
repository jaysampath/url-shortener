package com.project.url.shortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.url.shortener.entity.ShortenUrl;
import com.project.url.shortener.rest.request.LoginRequest;
import com.project.url.shortener.rest.response.SuccessfulLoginResponse;
import com.project.url.shortener.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String ADMIN_EMAIL = "admin@urlshortener.com";

    @Test
    public void testAdminAuthenticationInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(ADMIN_EMAIL);
        loginRequest.setPassword("something");

        mockMvc.perform(post(Endpoints.USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAdminEndpoints() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(ADMIN_EMAIL);
        loginRequest.setPassword("password");

        MvcResult result = mockMvc.perform(post(Endpoints.USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString =  result.getResponse().getContentAsString();
        SuccessfulLoginResponse loginResponse = objectMapper.readValue(responseString, SuccessfulLoginResponse.class);
        assertEquals(ADMIN_EMAIL, loginResponse.getUserEmail());
        assertNotNull(loginResponse.getAccessToken());

        String accessToken = "Bearer "+ loginResponse.getAccessToken();


        //test with invalid token
        mockMvc.perform(get(Endpoints.LIST_USERS).contentType(MediaType.APPLICATION_JSON)
                        .param("Authorization", accessToken + "oonjwbdjj"))
                .andExpect(status().isUnauthorized());

        // test /admin/list/users
         mockMvc.perform(get(Endpoints.LIST_USERS).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
                .andExpect(status().isOk());


        // test /admin/list/proxy
        mockMvc.perform(get(Endpoints.LIST_PROXIES).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", accessToken))
                .andExpect(status().isOk());

        //test user specific endpoints and get forbidden
        mockMvc.perform(get(Endpoints.LIST_USER_URLS)
                .header("Authorization", accessToken)
                .param("userEmail", "someone@email.com"))
                .andExpect(status().isForbidden());

    }
}
