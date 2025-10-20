package source.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import source.entity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
  Optional<UserCredential> findByLogin(String login);
}

