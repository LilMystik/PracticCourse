package source.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import source.entity.CardInfo;

import java.util.List;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

  @Query("SELECT c FROM CardInfo c WHERE c.id IN :ids")
  List<CardInfo> findCardsByIds(List<Long> ids);

  @Query(value = "SELECT * FROM card_info WHERE number = :number", nativeQuery = true)
  CardInfo findByNumberNative(String number);
}
