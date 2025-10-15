package source.service;

import source.entity.Order;

import java.util.List;

public interface OrderService {

  Order create(Order order);

  Order getById(Long id);

  List<Order> getByIds(List<Long> ids);

  List<Order> getByStatuses(List<String> statuses);

  Order update(Long id, Order order);

  void delete(Long id);
}
