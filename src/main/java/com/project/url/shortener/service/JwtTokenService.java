package com.project.url.shortener.service;

import com.project.url.shortener.commons.auth.UserDetailsImpl;
import com.project.url.shortener.entity.DBFeilds;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Value("${jwt.secret}")
    private String jwtSigningKey;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    public String generateToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        byte[] signingKey = jwtSigningKey.getBytes();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expirationTime).toInstant()))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setId(UUID.randomUUID().toString())
                .setSubject(userDetails.getEmail())
                .claim(DBFeilds.USERNAME, userDetails.getUsername())
                .claim("roles", roles)
                .compact();
    }

    public String getUserEmailFromJWT(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(jwtSigningKey.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return body.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSigningKey.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
            throw new JwtException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new JwtException("Expired JWT token. Please login again");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            throw new JwtException("JWT claims string is empty.");
        }
    }
}