package source.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import source.dto.*;
import source.service.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRegisterRequest req) {
    var tokens = authService.register(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest req) {
    var tokens = authService.login(req);
    return ResponseEntity.ok(tokens);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody TokenRefreshRequest req) {
    var tokens = authService.refresh(req);
    return ResponseEntity.ok(tokens);
  }

  @PostMapping("/validate")
  public ResponseEntity<TokenValidateResponse> validate(@Valid @RequestBody TokenValidateRequest req) {
    var result = authService.validate(req);
    return ResponseEntity.ok(result);
  }
}

