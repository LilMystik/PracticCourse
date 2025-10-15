package source.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.entity.Order;
import source.entity.OrderItem;
import source.exception.ResourceNotFoundException;
import source.repository.OrderRepository;
import source.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public Order create(Order order) {
    order.setCreationDate(LocalDateTime.now());
    // благодаря CascadeType.ALL у Order → OrderItem, сохраняются все связанные объекты
    return orderRepository.save(order);
  }

  @Override
  public Order getById(Long id) {
    return orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
  }

  @Override
  public List<Order> getByIds(List<Long> ids) {
    return orderRepository.findByIdIn(ids);
  }

  @Override
  public List<Order> getByStatuses(List<String> statuses) {
    return orderRepository.findByStatusIn(statuses);
  }

  @Override
  @Transactional
  public Order update(Long id, Order newOrder) {
    Order existing = getById(id);
    existing.setStatus(newOrder.getStatus());
    existing.getItems().clear();
    for (OrderItem item : newOrder.getItems()) {
      existing.addItem(item);
    }
    return orderRepository.save(existing);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!orderRepository.existsById(id)) {
      throw new ResourceNotFoundException("Order not found with id " + id);
    }
    orderRepository.deleteById(id);
  }
}
