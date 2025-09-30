package source.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CardInfoDto {
  private Long id;

  @NotBlank(message = "Номер карты обязателен")
  @Size(min = 12, max = 19, message = "Длина номера карты должна быть от 12 до 19 символов")
  private String number;

  @NotBlank(message = "Имя держателя карты обязательно")
  private String holder;

  @Future(message = "Срок действия должен быть в будущем")
  private LocalDate expirationDate;

  private Long userId;
}

