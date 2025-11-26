package com.devansh.erp_lite.services;

import com.devansh.erp_lite.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration_ms}")
    private long expiration_ms;

    private byte[] getSigningKey(){
        return secret.getBytes(StandardCharsets.UTF_8);
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getTitle());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration_ms))
                .signWith(Keys.hmacShaKeyFor(getSigningKey()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Boolean isTokenValid(String token, User user){
        final String username = extractUsername(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }

    public Boolean isTokenExpired(String token){
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
