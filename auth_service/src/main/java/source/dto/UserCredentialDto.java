package source.dto;

import lombok.Data;

@Data
public class UserCredentialDto {
  private Long id;
  private String login;
  private String email;
  private String passwordHash;
  private String salt;
}

