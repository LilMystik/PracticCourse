package source.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
  private Long id;

  @NotBlank(message = "Имя обязательно")
  private String name;

  @NotBlank(message = "Фамилия обязательна")
  private String surname;

  @Past(message = "Дата рождения должна быть в прошлом")
  private LocalDate birthDate;

  @Email(message = "Некорректный email")
  @NotBlank(message = "Email обязателен")
  private String email;

  private List<CardInfoDto> cards;
}
