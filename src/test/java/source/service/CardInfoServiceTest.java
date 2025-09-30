package source.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import source.entity.CardInfo;
import source.repository.CardInfoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardInfoServiceTest {

  @Mock
  private CardInfoRepository cardInfoRepository;

  @InjectMocks
  private CardInfoService cardInfoService;

  private CardInfo card;

  @BeforeEach
  void setUp() {
    card = new CardInfo();
    card.setId(1L);
    card.setNumber("1234-5678-9012-3456");
    card.setHolder("John Doe");
    card.setExpirationDate(LocalDate.of(2030, 12, 31));
  }

  @Test
  void testCreate() {
    when(cardInfoRepository.save(card)).thenReturn(card);

    CardInfo result = cardInfoService.create(card);

    assertEquals(card.getNumber(), result.getNumber());
    verify(cardInfoRepository, times(1)).save(card);
  }

  @Test
  void testGetById_found() {
    when(cardInfoRepository.findById(1L)).thenReturn(Optional.of(card));

    CardInfo result = cardInfoService.getById(1L);

    assertEquals(card.getNumber(), result.getNumber());
  }

  @Test
  void testGetById_notFound() {
    when(cardInfoRepository.findById(2L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class,
            () -> cardInfoService.getById(2L));
    assertEquals("Card not found", exception.getMessage());
  }

  @Test
  void testGetAll() {
    List<CardInfo> cards = List.of(card);
    when(cardInfoRepository.findAll()).thenReturn(cards);

    List<CardInfo> result = cardInfoService.getAll();

    assertEquals(1, result.size());
    assertEquals(card.getNumber(), result.get(0).getNumber());
  }

  @Test
  void testGetByIds() {
    List<CardInfo> cards = List.of(card);
    when(cardInfoRepository.findCardsByIds(List.of(1L))).thenReturn(cards);

    List<CardInfo> result = cardInfoService.getByIds(List.of(1L));

    assertEquals(1, result.size());
    assertEquals(card.getNumber(), result.get(0).getNumber());
  }

  @Test
  void testUpdate() {
    CardInfo newCard = new CardInfo();
    newCard.setNumber("9999-8888-7777-6666");
    newCard.setHolder("Jane Doe");
    newCard.setExpirationDate(LocalDate.of(2040, 12, 31));

    when(cardInfoRepository.findById(1L)).thenReturn(Optional.of(card));

    CardInfo updated = cardInfoService.update(1L, newCard);

    assertEquals("9999-8888-7777-6666", updated.getNumber());
    assertEquals("Jane Doe", updated.getHolder());
    assertEquals(LocalDate.of(2040, 12, 31), updated.getExpirationDate());
  }

  @Test
  void testUpdate_notFound() {
    CardInfo newCard = new CardInfo();
    when(cardInfoRepository.findById(2L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class,
            () -> cardInfoService.update(2L, newCard));
    assertEquals("Card not found", exception.getMessage());
  }

  @Test
  void testDelete() {
    doNothing().when(cardInfoRepository).deleteById(1L);

    cardInfoService.delete(1L);

    verify(cardInfoRepository, times(1)).deleteById(1L);
  }
}
