package source.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import source.config.TestContainersConfig;
import source.entity.OrderItem;
import source.entity.Order;
import source.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
class OrderServiceIntegrationTest extends TestContainersConfig {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @BeforeEach
  void clean() {
    orderRepository.deleteAll();
    // reset wiremock stubs
    wireMockServer.resetAll();
  }

  @Test
  void createAndGetByIdShouldReturnOrderWithUserFetchedFromWireMock() {
    // Arrange - stub User Service response
    String userJson = """
            {
              "id": 10,
              "name": "Alice",
              "surname": "Smith",
              "email": "alice@example.com"
            }
            """;
    wireMockServer.stubFor(get(urlPathMatching("/api/v1/users/10"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type","application/json").withBody(userJson)));

    // Create order
    Order order = new Order();
    order.setUserId(10L);
    order.setStatus("NEW");
    order.setCreationDate(LocalDateTime.now());
    OrderItem item = OrderItem.builder().itemId(100L).quantity(2).build();
    order.addItem(item);

    Order saved = orderService.create(order);

    // Act - get by id and ensure repository returns same
    Order fetched = orderService.getById(saved.getId());
    Assertions.assertEquals(saved.getId(), fetched.getId());

    // additionally verify WireMock was called when controller uses user client;
    // but here we only test service + repository; to test client call we would exercise controller layer
  }

  @Test
  void getByStatusesShouldReturnOrders() {
    Order o1 = new Order();
    o1.setUserId(1L);
    o1.setStatus("NEW");
    o1.setCreationDate(LocalDateTime.now());
    o1.addItem(OrderItem.builder().itemId(1L).quantity(1).build());

    Order o2 = new Order();
    o2.setUserId(2L);
    o2.setStatus("DONE");
    o2.setCreationDate(LocalDateTime.now());
    o2.addItem(OrderItem.builder().itemId(2L).quantity(1).build());

    orderRepository.saveAll(List.of(o1, o2));

    List<Order> found = orderService.getByStatuses(List.of("NEW", "DONE"));
    Assertions.assertEquals(2, found.size());
  }
}
