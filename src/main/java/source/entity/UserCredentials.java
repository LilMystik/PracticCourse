package source.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCredentials {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String login;

  @Column(nullable = false)
  private String passwordHash;
}
