package source.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import source.entity.User;
import source.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1L);
    user.setName("John");
    user.setSurname("Doe");
    user.setEmail("john.doe@example.com");
    user.setBirthDate(LocalDate.of(1990, 1, 1));
  }

  @Test
  void testCreate() {
    when(userRepository.save(user)).thenReturn(user);

    User result = userService.create(user);

    assertEquals("John", result.getName());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testGetById_found() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    User result = userService.getById(1L);

    assertEquals("John", result.getName());
  }

  @Test
  void testGetAll() {
    List<User> users = List.of(user);

    when(userRepository.findAllWithCards()).thenReturn(users);

    List<User> result = userService.getAll();

    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getName());

    verify(userRepository, times(1)).findAllWithCards();
  }

  @Test
  void testGetByIds() {
    List<User> users = List.of(user);
    when(userRepository.findUsersByIds(List.of(1L))).thenReturn(users);

    List<User> result = userService.getByIds(List.of(1L));

    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getName());
  }

  @Test
  void testUpdate() {
    User newUser = new User();
    newUser.setName("Jane");
    newUser.setSurname("Smith");
    newUser.setEmail("jane.smith@example.com");
    newUser.setBirthDate(LocalDate.of(1995, 5, 5));

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    User updated = userService.update(1L, newUser);

    assertEquals("Jane", updated.getName());
    assertEquals("Smith", updated.getSurname());
    assertEquals("jane.smith@example.com", updated.getEmail());
    assertEquals(LocalDate.of(1995, 5, 5), updated.getBirthDate());
  }

  @Test
  void testDelete() {
    doNothing().when(userRepository).deleteById(1L);

    userService.delete(1L);

    verify(userRepository, times(1)).deleteById(1L);
  }
}
