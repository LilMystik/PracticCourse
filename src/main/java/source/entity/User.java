package source.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String surname;

  private LocalDate birthDate;

  @Column(nullable = false, unique = true)
  private String email;

  @OneToMany(mappedBy = "user",
          cascade = CascadeType.ALL,
          orphanRemoval = true,
          fetch = FetchType.LAZY)
  private List<CardInfo> cards = new ArrayList<>();

  public void addCard(CardInfo card) {
    cards.add(card);
    card.setUser(this);
  }

  public void removeCard(CardInfo card) {
    cards.remove(card);
    card.setUser(null);
  }
}

