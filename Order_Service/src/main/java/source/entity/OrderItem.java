package source.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_items", schema = "orders_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(name = "item_id", nullable = false)
  private Long itemId;

  @Column(nullable = false)
  private Integer quantity;
}
