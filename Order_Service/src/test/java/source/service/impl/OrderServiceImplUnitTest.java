package source.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import source.entity.Order;
import source.entity.OrderItem;
import source.exception.ResourceNotFoundException;
import source.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplUnitTest {

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderServiceImpl orderService; // concrete impl under test

  private Order sampleOrder;

  @BeforeEach
  void setUp() {
    sampleOrder = Order.builder()
            .id(1L)
            .userId(10L)
            .status("NEW")
            .creationDate(LocalDateTime.now())
            .build();
    OrderItem item = OrderItem.builder()
            .id(100L)
            .itemId(200L)
            .quantity(2)
            .build();
    sampleOrder.addItem(item);
  }

  @Test
  void createShouldSetCreationDateAndSave() {
    when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
      Order o = inv.getArgument(0);
      o.setId(123L);
      return o;
    });

    Order result = orderService.create(new Order());
    assertNotNull(result.getCreationDate());
    assertEquals(123L, result.getId());
    verify(orderRepository, times(1)).save(any(Order.class));
  }

  @Test
  void getByIdWhenExists() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
    Order found = orderService.getById(1L);
    assertEquals(sampleOrder.getId(), found.getId());
    assertEquals("NEW", found.getStatus());
  }

  @Test
  void getByIdWhenNotFoundShouldThrow() {
    when(orderRepository.findById(2L)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> orderService.getById(2L));
  }

  @Test
  void getByIdsDelegatesToRepository() {
    List<Order> list = List.of(sampleOrder);
    when(orderRepository.findByIdIn(List.of(1L))).thenReturn(list);
    List<Order> result = orderService.getByIds(List.of(1L));
    assertEquals(1, result.size());
    verify(orderRepository).findByIdIn(List.of(1L));
  }

  @Test
  void getByStatusesDelegatesToRepository() {
    List<Order> list = List.of(sampleOrder);
    when(orderRepository.findByStatusIn(List.of("NEW"))).thenReturn(list);
    List<Order> result = orderService.getByStatuses(List.of("NEW"));
    assertEquals(1, result.size());
    verify(orderRepository).findByStatusIn(List.of("NEW"));
  }

  @Test
  void updateShouldReplaceItemsAndStatus() {
    Order updated = new Order();
    updated.setStatus("DONE");
    OrderItem newItem = OrderItem.builder().itemId(777L).quantity(1).build();
    updated.setItems(List.of(newItem));

    // repo returns existing
    when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
    when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

    Order result = orderService.update(1L, updated);

    assertEquals("DONE", result.getStatus());
    assertEquals(1, result.getItems().size());
    assertEquals(777L, result.getItems().get(0).getItemId());
    verify(orderRepository).save(any(Order.class));
  }

  @Test
  void updateWhenNotFoundThrows() {
    when(orderRepository.findById(99L)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> orderService.update(99L, new Order()));
  }

  @Test
  void deleteShouldCallRepository() {
    when(orderRepository.existsById(1L)).thenReturn(true);
    doNothing().when(orderRepository).deleteById(1L);
    orderService.delete(1L);
    verify(orderRepository).deleteById(1L);
  }

  @Test
  void deleteWhenNotFoundThrows() {
    when(orderRepository.existsById(2L)).thenReturn(false);
    assertThrows(ResourceNotFoundException.class, () -> orderService.delete(2L));
  }
}

