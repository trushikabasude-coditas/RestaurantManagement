package com.example.RestaurantManagement.filter;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JwtUtil {
    @Value("${app.jwt.secret}")
    private  String secret;
    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private final Set<String> blacklist= ConcurrentHashMap.newKeySet();
    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    private String generateToken(String username) {
        return jwts.builder()
                subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key())
                .compact();
    }
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean validate(String token) {
        if (blacklist.contains(token)) return false;
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    }
