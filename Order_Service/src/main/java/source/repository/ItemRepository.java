package source.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import source.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {}

