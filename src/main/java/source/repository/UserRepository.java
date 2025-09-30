package source.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import source.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.id IN :ids")
  List<User> findUsersByIds(List<Long> ids);

  @Query(value = "SELECT * FROM users WHERE surname = :surname", nativeQuery = true)
  List<User> findBySurnameNative(String surname);

  @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.cards")
  List<User> findAllWithCards();
}