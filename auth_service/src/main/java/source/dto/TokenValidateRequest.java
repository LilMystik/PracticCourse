package source.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenValidateRequest {

  @NotBlank(message = "Access token is required")
  private String accessToken;
}


