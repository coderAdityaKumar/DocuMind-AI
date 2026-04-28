package com.aditya.documind.Security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTservice {
    private final String secret="myjwtsecretNowFine404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generateToken(Long userId,Long tenantId,String role){
        return Jwts.builder()
        .setSubject(userId.toString())
        .claim("userId", userId)
        .claim("tenantId", tenantId)
        .claim("role", role)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+86400000))
        .signWith(SignatureAlgorithm.HS256,secret)
        .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    }
}
