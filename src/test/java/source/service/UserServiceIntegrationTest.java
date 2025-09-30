package source.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import source.entity.User;
import source.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Testcontainers
class UserServiceIntegrationTest {

  @Container
  public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("testdb")
          .withUsername("user")
          .withPassword("pass");

  @Container
  public static GenericContainer<?> redis = new GenericContainer<>("redis:7")
          .withExposedPorts(6379);

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void testCreateAndGetById() {
    User user = new User();
    user.setName("Alice");
    user.setSurname("Smith");
    user.setEmail("alice@example.com");
    user.setBirthDate(LocalDate.of(1990, 1, 1));
    user.setCards(List.of());

    User saved = userService.create(user);
    User fetched = userService.getById(saved.getId());

    Assertions.assertEquals("Alice", fetched.getName());
  }

  @Test
  void testCachingWorks() {
    User user = new User();
    user.setName("Alice");
    user.setSurname("Smith");
    user.setEmail("alice@example.com");
    user.setBirthDate(LocalDate.of(1990, 1, 1));
    user.setCards(List.of());

    User saved = userService.create(user);

    User fetched1 = userService.getById(saved.getId());

    User fetched2 = userService.getById(saved.getId());

    Assertions.assertEquals(fetched1.getId(), fetched2.getId());
  }
}
