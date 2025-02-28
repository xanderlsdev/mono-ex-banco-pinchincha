package com.bancopichincha.exchangeapi.exchange_api.jwt;

import com.bancopichincha.exchangeapi.exchange_api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final String SECRET_KEY =
    "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
  private final long validityInMilliseconds = 3600000; // 1 hora

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts
      .builder()
      .setClaims(claims)
      .setSubject(user.getName())
      .setIssuedAt(now)
      .setExpiration(validity)
      .signWith(getKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts
        .parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token);

      return !claims.getBody().getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      System.out.println("Token expirado");
    } catch (MalformedJwtException | SignatureException e) {
      System.out.println("Token inválido");
    } catch (Exception e) {
      System.out.println("Error al validar token: " + e.getMessage());
    }
    return false;
  }

  public String getEmailFromToken(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getKey())
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }
}
