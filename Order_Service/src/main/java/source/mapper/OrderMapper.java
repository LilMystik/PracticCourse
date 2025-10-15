package source.mapper;


import org.mapstruct.Mapper;
import source.dto.OrderCreateDto;
import source.dto.OrderDto;
import source.dto.OrderItemDto;
import source.entity.Order;
import source.entity.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  OrderDto toDto(Order order);

  Order toEntity(OrderDto dto);

  Order toEntity(OrderCreateDto createDto);

  List<OrderDto> toOrderDtoList(List<OrderItem> orders);

  OrderItem toEntity(OrderItemDto dto);

  OrderItemDto toDto(OrderItem entity);

  List<OrderItemDto> toItemDtoList(List<OrderItem> items);
}
