package com.example.leetCodeRepetition.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    private final MyLogger logger = new MyLogger();

    public String generateToken(String email, String id) {
        logger.debug("Generating JWT: " + SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        long oneMonthInMillis = 1000L * 60 * 60 * 24 * 30; // 1 month in milliseconds
        String jwt = Jwts.builder()
                .setSubject(email)
                .claim("id", id) // add id to the JWT claims
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + oneMonthInMillis)) // 1 month expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }
    public boolean validateToken(String jwtToken, String email){
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException e) {
            logger.error("JWT validation failed: " + e.getMessage());
            return false;
        }
    }

    public String getEmailFromToken(String jwtToken) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken);
        return claimsJws.getBody().getSubject();
    }

    public String getIdFromToken(String jwtToken){
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken);
        return claimsJws.getBody().get("id").toString();
    }



}