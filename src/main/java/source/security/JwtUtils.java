package source.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private final long accessTokenValidity = 15 * 60 * 1000;
  private final long refreshTokenValidity = 7 * 24 * 60 * 60 * 1000;

  public String generateAccessToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
            .signWith(key)
            .compact();
  }

  public String generateRefreshToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
            .signWith(key)
            .compact();
  }

  public String validateToken(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    } catch (JwtException e) {
      return null;
    }
  }
}
