package source.dto;

import lombok.Data;

@Data
public class OrderWithUserDto {
  private OrderDto order;
  private UserInfoDto user;
}

