package source.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserCreateDto {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Surname is required")
  private String surname;

  @Past(message = "Birth date must be in the past")
  private LocalDate birthDate;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email is required")
  private String email;

  private List<CardInfoCreateDto> cards;
}
