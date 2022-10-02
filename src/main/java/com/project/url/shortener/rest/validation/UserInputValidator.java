package com.project.url.shortener.rest.validation;

import com.project.url.shortener.exception.InvalidInputException;
import com.project.url.shortener.rest.request.LoginRequest;
import com.project.url.shortener.rest.request.SignupRequest;
import com.project.url.shortener.rest.request.UpdatePasswordRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Component
public class UserInputValidator {


    public void validateSignUpRequest(SignupRequest signupRequest){
        if(!this.validateEmail(signupRequest.getEmail()))
            throw new InvalidInputException("Invalid email -" + signupRequest.getEmail());

        if(!this.validatePassword(signupRequest.getPassword()))
            throw new InvalidInputException("Password should contain atleast 6 characters");

        if(!this.validateUsername(signupRequest.getUsername()))
            throw new InvalidInputException("username must not be null or blank or less than three characters");

    }

    public boolean validateEmail(String email){
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.matches(regexPattern, email);
    }

    public boolean validateUsername(String username){
        return StringUtils.hasText(username) && username.length() >=3;
    }

    public boolean validatePassword(String password){
        return StringUtils.hasText(password) && password.length() >=6;
    }

    public void validateLoginRequest(LoginRequest loginRequest){
        if(!this.validateEmail(loginRequest.getEmail()))
            throw new InvalidInputException("Invalid email -" + loginRequest.getEmail());

        if(!this.validatePassword(loginRequest.getPassword()))
            throw new InvalidInputException("Password should contain atleast 6 characters");
    }

    public void validateUpdatePasswordRequest(UpdatePasswordRequest updatePasswordRequest){
        LoginRequest loginRequest= new LoginRequest();
        loginRequest.setPassword(updatePasswordRequest.getNewPassword());
        loginRequest.setEmail(updatePasswordRequest.getEmail());

        this.validateLoginRequest(loginRequest);
    }

}
