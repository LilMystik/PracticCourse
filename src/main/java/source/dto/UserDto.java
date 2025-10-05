package source.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
  private Long id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Surname is required")
  private String surname;

  @Past(message = "It's suuposed to be in the past")
  private LocalDate birthDate;

  @Email(message = "Incorrect email")
  @NotBlank(message = "Email is required")
  private String email;

  private List<CardInfoDto> cards;
}
