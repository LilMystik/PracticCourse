package source.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CardInfoDto {
  private Long id;

  @NotBlank(message = "Card number is required")
  @Size(min = 12, max = 19, message = "Card number length is have to be from 12 to 19 characters")
  private String number;

  @NotBlank(message = "Name of card hold is required")
  private String holder;

  @Future(message = "It's supposed to be in the future")
  private LocalDate expirationDate;

  private Long userId;
}

