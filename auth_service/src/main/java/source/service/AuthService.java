package source.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import source.dto.*;
import source.entity.UserCredential;
import source.repository.UserCredentialRepository;
import source.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserCredentialRepository credentialRepository;
  private final JwtService jwtService;
  private final WebClient webClient; // для user-service REST вызова

  public AuthResponse register(AuthRegisterRequest req) {
    // 1️⃣ Хэшируем пароль с солью
    String salt = BCrypt.gensalt();
    String hash = BCrypt.hashpw(req.getPassword(), salt);

    // 2️⃣ Сохраняем учётные данные в auth БД
    UserCredential cred = new UserCredential();
    cred.setLogin(req.getLogin());
    cred.setEmail(req.getEmail());
    cred.setPasswordHash(hash);
    cred.setSalt(salt);
    credentialRepository.save(cred);

    webClient.post()
            .uri("http://user-service:8080/api/v1/users")
            .bodyValue(new UserCreateDto(req.getName(), req.getSurname(), req.getEmail()))
            .retrieve()
            .toBodilessEntity()
            .block();

    String access = jwtService.generateAccessToken(req.getLogin());
    String refresh = jwtService.generateRefreshToken(req.getLogin());

    return new AuthResponse(access, refresh);
  }

  public AuthResponse login(AuthLoginRequest req) {
    UserCredential cred = credentialRepository.findByLogin(req.getLogin())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!BCrypt.checkpw(req.getPassword(), cred.getPasswordHash()))
      throw new RuntimeException("Invalid password");

    return new AuthResponse(
            jwtService.generateAccessToken(req.getLogin()),
            jwtService.generateRefreshToken(req.getLogin())
    );
  }

  public AuthResponse refresh(TokenRefreshRequest req) {
    String login = jwtService.validateAndExtractLogin(req.getRefreshToken());
    return new AuthResponse(
            jwtService.generateAccessToken(login),
            jwtService.generateRefreshToken(login)
    );
  }

  public TokenValidateResponse validate(TokenValidateRequest req) {
    try {
      String login = jwtService.validateAndExtractLogin(req.getAccessToken());
      var user = credentialRepository.findByLogin(login);
      return new TokenValidateResponse(user.map(UserCredential::getId).orElse(null), login, true);
    } catch (Exception e) {
      return new TokenValidateResponse(null, null, false);
    }
  }
}


