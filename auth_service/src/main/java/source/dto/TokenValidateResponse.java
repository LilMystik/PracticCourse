package source.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenValidateResponse {
  private Long userId;
  private String login;
  private boolean valid;
}
