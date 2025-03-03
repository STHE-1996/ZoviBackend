package com.zovi.assessment.sthembisobuthelezi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

public class JwtUtil {
    private String secret = "wertyuioppoiytrertyu"; // Use a strong key and keep it safe

    public String generateToken( String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId); // Include userId in the claims

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getDecoder().decode(secret))
                .parseClaimsJws(token)
                .getBody();
    }
}

