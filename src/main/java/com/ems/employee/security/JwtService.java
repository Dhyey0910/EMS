package com.ems.employee.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    public String generateToken(String email, String role) {

        Date issuedAt = new Date();
        Date expiration = new Date(
                issuedAt.getTime() + 60 * 60 * 1000
        );

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }
}