package com.project.url.shortener.commons.auth;

import com.project.url.shortener.entity.User;
import com.project.url.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(String.format("email - %s is not registered. Please sign up", email));
        }
        return convertUserToUserDetails(user);
    }

    private UserDetailsImpl convertUserToUserDetails(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setUsername(user.getUsername());
        userDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        return userDetails;
    }
}
