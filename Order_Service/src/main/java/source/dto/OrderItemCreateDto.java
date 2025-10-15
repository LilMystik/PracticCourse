package source.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemCreateDto {

  @NotNull(message = "Item ID is required")
  private Long itemId;

  @Positive(message = "Quantity must be positive")
  private Integer quantity;
}