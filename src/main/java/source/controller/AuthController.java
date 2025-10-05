package source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import source.entity.UserCredentials;
import source.repository.UserCredentialsRepository;
import source.security.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authManager;
  private final UserCredentialsRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestParam String login, @RequestParam String password) {
    if (repository.findByLogin(login).isPresent()) {
      return ResponseEntity.badRequest().body("Login already exists");
    }
    String hash = passwordEncoder.encode(password);
    repository.save(new UserCredentials(null, login, hash));
    return ResponseEntity.ok("User registered");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
    try {
      authManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
      String accessToken = jwtUtils.generateAccessToken(login);
      String refreshToken = jwtUtils.generateRefreshToken(login);
      return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
    String username = jwtUtils.validateToken(refreshToken);
    if (username == null) return ResponseEntity.status(401).body("Invalid refresh token");
    String newAccessToken = jwtUtils.generateAccessToken(username);
    return ResponseEntity.ok(new TokenResponse(newAccessToken, refreshToken));
  }

  // DTO
  @lombok.Data
  @lombok.AllArgsConstructor
  static class TokenResponse {
    private String accessToken;
    private String refreshToken;
  }
}
