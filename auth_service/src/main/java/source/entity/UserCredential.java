package source.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "user_credentials", schema = "auth_schema")
@Data
public class UserCredential {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String login;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private String salt;
}


