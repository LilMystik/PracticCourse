package source.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

  @Value("${security.jwt.secret}")
  private Key key;

  @Value("${security.jwt.expiration}")
  private long accessExpiration;

  @Value("${security.jwt.refresh-expiration}")
  private long refreshExpiration;

  public String generateAccessToken(String login) {
    return Jwts.builder()
            .setSubject(login)
            .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
            .signWith(key)
            .compact();
  }

  public String generateRefreshToken(String login) {
    return Jwts.builder()
            .setSubject(login)
            .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(key)
            .compact();
  }

  public String validateAndExtractLogin(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }
}

