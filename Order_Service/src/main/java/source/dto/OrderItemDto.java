package source.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDto {
  private Long id;

  @NotNull
  private Long itemId;

  @NotNull
  @Min(1)
  private Integer quantity;
}
