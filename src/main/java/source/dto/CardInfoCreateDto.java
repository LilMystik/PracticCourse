package source.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CardInfoCreateDto {

  @NotBlank(message = "Card number is required")
  @Size(min = 12, max = 19, message = "Card number length must be between 12 and 19 characters")
  private String number;

  @NotBlank(message = "Card holder name is required")
  private String holder;

  @Future(message = "Expiration date must be in the future")
  private LocalDate expirationDate;

  private Long userId;
}
