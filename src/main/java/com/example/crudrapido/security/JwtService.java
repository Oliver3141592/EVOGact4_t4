package com.example.crudrapido.security;
 
import java.nio.charset.StandardCharsets;
import java.util.Date;
 
import javax.crypto.SecretKey;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
 
@Service
public class JwtService {
 
    @Value("${jwt.secret}")
    private String secret;
 
    @Value("${jwt.expiration}")
    private long expiration;
 
    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
 
    public String generateToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(key())
                .compact();
    }
 
    // Lanza excepción si el token es falso o está vencido
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}