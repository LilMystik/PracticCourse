package source.client;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import source.dto.UserInfoDto;

@Component
@RequiredArgsConstructor
public class UserClient {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${user.service.url}")
  private String userServiceUrl;

  public UserInfoDto getUserByEmail(String email) {
    String url = userServiceUrl + "/by-email?email=" + email;
    ResponseEntity<UserInfoDto> resp = restTemplate.getForEntity(url, UserInfoDto.class);
    if (resp.getStatusCode().is2xxSuccessful()) {
      return resp.getBody();
    }
    return null;
  }

  public UserInfoDto getUserById(Long id) {
    String url = userServiceUrl + "/" + id;
    ResponseEntity<UserInfoDto> resp = restTemplate.getForEntity(url, UserInfoDto.class);
    if (resp.getStatusCode().is2xxSuccessful()) {
      return resp.getBody();
    }
    return null;
  }
}

