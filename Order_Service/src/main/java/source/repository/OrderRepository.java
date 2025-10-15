package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import source.entity.Order;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByIdIn(List<Long> ids);

  List<Order> findByStatusIn(List<String> statuses);

  @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id = :id")
  Order findByIdWithItems(Long id);
}
