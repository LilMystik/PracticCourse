package project.service;

import project.model.Customer;
import project.model.Order;
import project.model.OrderItem;
import project.model.OrderStatus;
import java.util.*;
import java.util.stream.Collectors;

public class MetricsService {

  public Set<String> getUniqueCities(List<Order> orders) {
    return orders.stream()
            .map(order -> order.getCustomer().getCity())
            .collect(Collectors.toSet());
  }

  public double getTotalIncomeFromCompletedOrders(List<Order> orders) {
    return orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
            .flatMap(order -> order.getItems().stream())
            .mapToDouble(OrderItem::getTotalPrice)
            .sum();
  }

  public String getMostPopularProduct(List<Order> orders) {
    return orders.stream()
            .flatMap(order -> order.getItems().stream())
            .collect(Collectors.groupingBy(
                    OrderItem::getProductName,
                    Collectors.summingInt(OrderItem::getQuantity)
            ))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("No products found");
  }

  public double getAverageCheckForDeliveredOrders(List<Order> orders) {
    return orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
            .mapToDouble(order -> order.getItems().stream()
                    .mapToDouble(OrderItem::getTotalPrice)
                    .sum())
            .average()
            .orElse(0.0);
  }

  public List<Customer> getCustomersWithMoreThan5Orders(List<Order> orders) {
    Map<Customer, Long> customerOrderCount = orders.stream()
            .collect(Collectors.groupingBy(
                    Order::getCustomer,
                    Collectors.counting()
            ));
    return customerOrderCount.entrySet().stream()
            .filter(entry -> entry.getValue() > 5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
  }
}