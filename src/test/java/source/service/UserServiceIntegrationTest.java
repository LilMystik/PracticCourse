package source.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import source.service.config.TestContainersConfig;
import source.entity.User;
import source.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class UserServiceIntegrationTest extends TestContainersConfig {

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
