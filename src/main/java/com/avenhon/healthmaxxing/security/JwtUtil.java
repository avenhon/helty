package com.avenhon.healthmaxxing.security;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.avenhon.healthmaxxing.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String JWT_SECRET;

  @Value("${jwt.access_token_expiration}")
  private long ACCESS_TOKEN_EXPIRATION_MS;

  @Value("${jwt.refresh_token_expiration}")
  private long REFRESH_TOKEN_EXPIRATION_MS;

  private SecretKey key;

  @PostConstruct
  public void init() {
    key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
  }

  public String generateAccessToken(User user) {
    return generateToken(user, ACCESS_TOKEN_EXPIRATION_MS);
  }

  public String generateRefreshToken(User user) {
    return generateToken(user, REFRESH_TOKEN_EXPIRATION_MS);
  }

  public String generateToken(User user, long expiration) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId());
    claims.put("roles", user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));

    return Jwts.builder()
            .claims(claims)
            .subject(user.getUsername())
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + expiration))
            .signWith(key)
            .compact();
  }

  private Claims getAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  public String getUserFromToken(String token) {
    return getAllClaims(token).getSubject();
  }

  public Long getUserIdFromToken(String token) {
    return ((Number) getAllClaims(token).get("userId")).longValue();
  }

  public List<String> getRolesFromToken(String token) {
    return (List<String>) getAllClaims(token).get("roles");
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
  }
}
