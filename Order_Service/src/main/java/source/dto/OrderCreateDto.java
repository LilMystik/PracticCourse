package source.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDto {
  @NotNull
  private Long userId;

  @NotNull
  private String status;

  @NotEmpty
  private List<OrderItemDto> items;
}

