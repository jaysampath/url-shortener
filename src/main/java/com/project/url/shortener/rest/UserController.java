package com.project.url.shortener.rest;

import com.project.url.shortener.commons.request.LoginRequest;
import com.project.url.shortener.commons.request.UpdatePasswordRequest;
import com.project.url.shortener.commons.response.SuccessfulLoginResponse;
import com.project.url.shortener.entity.User;
import com.project.url.shortener.exception.JWTException;
import com.project.url.shortener.exception.UserAlreadyExistsException;
import com.project.url.shortener.exception.UserNotFoundException;
import com.project.url.shortener.service.JwtTokenProvider;
import com.project.url.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public SuccessfulLoginResponse userRegister(@RequestBody User user) {

        if (userService.isUserExists(user.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email - %s is already registered. Please login with the same.", user.getEmail()));
        }

        String actualPassword = user.getPassword();

        //encrypt the password
        user.setPassword(passwordEncoder.encode(actualPassword));
        userService.saveUser(user);

        String token = authenticateAndGetToken(user.getEmail(), actualPassword);

        return new SuccessfulLoginResponse(user.getEmail(), token, user.getUsername(),
                StringUtils.hasText(user.getPrettyName()) ? user.getPrettyName() : user.getUsername());
    }

    @PostMapping("/login")
    public SuccessfulLoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new UserNotFoundException(String.format(
                    "Email - %s is not registered. Please register with the same.", loginRequest.getEmail()));
        }

        String token = authenticateAndGetToken(loginRequest.getEmail(), loginRequest.getPassword());

        return new SuccessfulLoginResponse(user.getEmail(), token, user.getUsername(), user.getPrettyName());
    }

    @PostMapping("/update")
    public SuccessfulLoginResponse updateUserPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {

        User user = userService.getUserByEmail(updatePasswordRequest.getEmail());
        if (user == null) {
            throw new UserNotFoundException(String.format(
                    "Email - %s is not registered. Please register with the same.", updatePasswordRequest.getEmail()));
        }

        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userService.updateUserPassword(user);
        String token = authenticateAndGetToken(user.getEmail(), updatePasswordRequest.getNewPassword());
        return new SuccessfulLoginResponse(user.getEmail(), token, user.getUsername(), user.getPrettyName());
    }

    @PostMapping("/validate")
    public SuccessfulLoginResponse validateToken(@RequestParam String accessToken) {
        boolean isValid = tokenProvider.validateToken(accessToken);
        if (!isValid) {
            throw new JWTException("Invalid token or token expired. Please login in");
        }
        String email = tokenProvider.getUserEmailFromJWT(accessToken);

        User user = userService.getUserByEmail(email);
        return new SuccessfulLoginResponse(user.getEmail(), accessToken, user.getUsername(), user.getPrettyName());
    }

    private String authenticateAndGetToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        return tokenProvider.generateToken(authentication);
    }
}
