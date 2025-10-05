package source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import source.entity.UserCredentials;
import source.repository.UserCredentialsRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserCredentialsRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserCredentials creds = repository.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return User.builder()
            .username(creds.getLogin())
            .password(creds.getPasswordHash())
            .roles("USER")
            .build();
  }
}
