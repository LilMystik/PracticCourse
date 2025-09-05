package project.service;

import project.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class OrderMetricsServiceTest {

  private MetricsService metricsService;
  private List<Order> testOrders;

  @BeforeEach
  void setUp() {
    metricsService = new MetricsService();
    testOrders = createTestOrders();
  }

  private List<Order> createTestOrders() {
    Customer customer1 = new Customer("C1", "John Doe", "john@email.com",
            LocalDateTime.of(2023, 1, 1, 0, 0), 30, "New York");
    Customer customer2 = new Customer("C2", "Jane Smith", "jane@email.com",
            LocalDateTime.of(2023, 2, 1, 0, 0), 25, "Los Angeles");
    Customer customer3 = new Customer("C3", "Bob Johnson", "bob@email.com",
            LocalDateTime.of(2023, 3, 1, 0, 0), 35, "Chicago");
    Customer customer4 = new Customer("C4", "Alice Brown", "alice@email.com",
            LocalDateTime.of(2023, 4, 1, 0, 0), 28, "New York");
    OrderItem laptop = new OrderItem("Laptop", 1, 1200.0, Category.ELECTRONICS);
    OrderItem phone = new OrderItem("Smartphone", 2, 800.0, Category.ELECTRONICS);
    OrderItem shirt = new OrderItem("T-Shirt", 3, 25.0, Category.CLOTHING);
    OrderItem book = new OrderItem("Java Book", 1, 45.0, Category.BOOKS);
    OrderItem shoes = new OrderItem("Sneakers", 1, 89.0, Category.CLOTHING);
    OrderItem tablet = new OrderItem("Tablet", 1, 450.0, Category.ELECTRONICS);
    Order order1 = new Order("O1", LocalDateTime.of(2023, 5, 1, 10, 0),
            customer1, Arrays.asList(laptop, book), OrderStatus.DELIVERED);
    Order order2 = new Order("O2", LocalDateTime.of(2023, 5, 2, 11, 0),
            customer2, Arrays.asList(phone, shirt), OrderStatus.DELIVERED);
    Order order3 = new Order("O3", LocalDateTime.of(2023, 5, 3, 12, 0),
            customer3, Arrays.asList(shoes), OrderStatus.PROCESSING);
    Order order4 = new Order("O4", LocalDateTime.of(2023, 5, 4, 13, 0),
            customer4, Arrays.asList(tablet, shirt), OrderStatus.DELIVERED);
    Order order5 = new Order("O5", LocalDateTime.of(2023, 5, 5, 14, 0),
            customer1, Arrays.asList(book), OrderStatus.CANCELLED);
    Order order6 = new Order("O6", LocalDateTime.of(2023, 5, 6, 15, 0),
            customer1, Arrays.asList(laptop), OrderStatus.DELIVERED);
    Order order7 = new Order("O7", LocalDateTime.of(2023, 5, 7, 16, 0),
            customer1, Arrays.asList(phone), OrderStatus.DELIVERED);
    Order order8 = new Order("O8", LocalDateTime.of(2023, 5, 8, 17, 0),
            customer1, Arrays.asList(shirt), OrderStatus.DELIVERED);
    Order order9 = new Order("O9", LocalDateTime.of(2023, 5, 9, 18, 0),
            customer1, Arrays.asList(shoes), OrderStatus.DELIVERED);
    Order order10 = new Order("O10", LocalDateTime.of(2023, 5, 10, 19, 0),
            customer1, Arrays.asList(book), OrderStatus.DELIVERED);
    return Arrays.asList(order1, order2, order3, order4, order5, order6,
            order7, order8, order9, order10);
  }

  @Test
  void testGetUniqueCities() {
    Set<String> uniqueCities = metricsService.getUniqueCities(testOrders);
    assertEquals(3, uniqueCities.size());
    assertTrue(uniqueCities.contains("New York"));
    assertTrue(uniqueCities.contains("Los Angeles"));
    assertTrue(uniqueCities.contains("Chicago"));
  }

  @Test
  void testGetTotalIncomeFromCompletedOrders() {
    double totalIncome = metricsService.getTotalIncomeFromCompletedOrders(testOrders);
    double expected = 1245 + 1675 + 525 + 1200 + 1600 + 75 + 89 + 45;
    assertEquals(expected, totalIncome, 0.001);
  }

  @Test
  void testGetMostPopularProduct() {
    String mostPopular = metricsService.getMostPopularProduct(testOrders);
    assertEquals("T-Shirt", mostPopular);
  }

  @Test
  void testGetAverageCheckForDeliveredOrders() {
    double averageCheck = metricsService.getAverageCheckForDeliveredOrders(testOrders);
    double total = 1245 + 1675 + 525 + 1200 + 1600 + 75 + 89 + 45;
    double expectedAverage = total / 8;
    assertEquals(expectedAverage, averageCheck, 0.001);
  }

  @Test
  void testGetCustomersWithMoreThan5Orders() {
    List<Customer> customers = metricsService.getCustomersWithMoreThan5Orders(testOrders);
    assertEquals(1, customers.size());
    assertEquals("John Doe", customers.get(0).getName());
  }
}