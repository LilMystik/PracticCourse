package source.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import source.client.UserClient;
import source.dto.OrderCreateDto;
import source.dto.OrderWithUserDto;
import source.dto.UserInfoDto;
import source.entity.Order;
import source.mapper.OrderMapper;
import source.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final OrderMapper mapper;
  private final UserClient userClient;

  @PostMapping
  public ResponseEntity<OrderWithUserDto> create(@Valid @RequestBody OrderCreateDto dto) {
    Order order = mapper.toEntity(dto);
    Order saved = orderService.create(order);
    UserInfoDto user = userClient.getUserById(saved.getUserId());

    OrderWithUserDto resp = new OrderWithUserDto();
    resp.setOrder(mapper.toDto(saved));
    resp.setUser(user);
    return ResponseEntity.status(201).body(resp);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderWithUserDto> getById(@PathVariable Long id) {
    Order order = orderService.getById(id);
    UserInfoDto user = userClient.getUserById(order.getUserId());

    OrderWithUserDto resp = new OrderWithUserDto();
    resp.setOrder(mapper.toDto(order));
    resp.setUser(user);
    return ResponseEntity.ok(resp);
  }

  @GetMapping
  public ResponseEntity<List<OrderWithUserDto>> getByParams(
          @RequestParam(required = false) List<Long> ids,
          @RequestParam(required = false) List<String> statuses) {

    List<Order> orders;
    if (ids != null && !ids.isEmpty()) {
      orders = orderService.getByIds(ids);
    } else if (statuses != null && !statuses.isEmpty()) {
      orders = orderService.getByStatuses(statuses);
    } else {
      orders = List.of();
    }

    List<OrderWithUserDto> resp = orders.stream()
            .map(o -> {
              OrderWithUserDto dto = new OrderWithUserDto();
              dto.setOrder(mapper.toDto(o));
              dto.setUser(userClient.getUserById(o.getUserId()));
              return dto;
            })
            .collect(Collectors.toList());

    return ResponseEntity.ok(resp);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderWithUserDto> update(@PathVariable Long id, @Valid @RequestBody OrderCreateDto dto) {
    Order newOrder = mapper.toEntity(dto);
    Order updated = orderService.update(id, newOrder);
    UserInfoDto user = userClient.getUserById(updated.getUserId());

    OrderWithUserDto resp = new OrderWithUserDto();
    resp.setOrder(mapper.toDto(updated));
    resp.setUser(user);
    return ResponseEntity.ok(resp);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    orderService.delete(id);
    return ResponseEntity.noContent().build();
  }
}

