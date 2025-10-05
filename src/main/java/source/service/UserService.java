package source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.entity.User;
import source.exception.ResourceNotFoundException;
import source.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  @CachePut(value = "users", key = "#result.id")
  public User create(User user) {
    return userRepository.save(user);
  }

  @Cacheable(value = "users", key = "#id")
  @Transactional(readOnly = true)
  public User getById(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    user.getCards().size();
    return user;
  }

  @Cacheable(value = "users", key = "'all'")
  public List<User> getAll() {
    return userRepository.findAllWithCards();
  }

  public List<User> getByIds(List<Long> ids) {
    return userRepository.findUsersByIds(ids);
  }

  @Transactional
  @CachePut(value = "users", key = "#id")
  public User update(Long id, User newUser) {
    User user = getById(id);
    user.setName(newUser.getName());
    user.setSurname(newUser.getSurname());
    user.setBirthDate(newUser.getBirthDate());
    user.setEmail(newUser.getEmail());
    return user;
  }

  @Transactional
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}


