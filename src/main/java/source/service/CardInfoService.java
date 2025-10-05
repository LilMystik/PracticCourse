package source.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.entity.CardInfo;
import source.repository.CardInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardInfoService {

  private final CardInfoRepository cardInfoRepository;

  public CardInfo create(CardInfo card) {
    return cardInfoRepository.save(card);
  }

  public CardInfo getById(Long id) {
    return cardInfoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Card not found"));
  }

  public List<CardInfo> getAll() {
    return cardInfoRepository.findAll();
  }

  public List<CardInfo> getByIds(List<Long> ids) {
    return cardInfoRepository.findCardsByIds(ids);
  }

  @Transactional
  public CardInfo update(Long id, CardInfo newCard) {
    CardInfo card = getById(id);
    card.setNumber(newCard.getNumber());
    card.setHolder(newCard.getHolder());
    card.setExpirationDate(newCard.getExpirationDate());
    return card;
  }

  @Transactional
  public void delete(Long id) {
    cardInfoRepository.deleteById(id);
  }
}


