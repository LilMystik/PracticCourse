package source.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import source.entity.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
