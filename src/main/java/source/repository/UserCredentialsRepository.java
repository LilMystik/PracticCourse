package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import source.entity.UserCredentials;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
  Optional<UserCredentials> findByLogin(String login);
}
