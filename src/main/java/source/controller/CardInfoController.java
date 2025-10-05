package source.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import source.controller.base.BaseController;
import source.dto.CardInfoCreateDto;
import source.dto.CardInfoDto;
import source.entity.CardInfo;
import source.exception.ResourceNotFoundException;
import source.mapper.CardInfoMapper;
import source.service.CardInfoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardInfoController implements BaseController<CardInfoCreateDto, CardInfoDto> {

  private final CardInfoService cardService;
  private final CardInfoMapper cardMapper;

  @Override
  public ResponseEntity<CardInfoDto> create(@Valid @RequestBody CardInfoCreateDto cardCreateDto) {
    CardInfo card = cardMapper.toEntity(cardCreateDto);
    CardInfo saved = cardService.create(card);
    return ResponseEntity.status(HttpStatus.CREATED).body(cardMapper.toDto(saved));
  }

  @Override
  public ResponseEntity<CardInfoDto> getById(Long id) {
    CardInfo card = cardService.getById(id);
    if (card == null) throw new ResourceNotFoundException("Card not found with id " + id);
    return ResponseEntity.ok(cardMapper.toDto(card));
  }

  @Override
  public ResponseEntity<List<CardInfoDto>> getAllOrByIds(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return ResponseEntity.ok(cardMapper.toDtoList(cardService.getAll()));
    }
    return ResponseEntity.ok(cardMapper.toDtoList(cardService.getByIds(ids)));
  }

  @Override
  public ResponseEntity<CardInfoDto> update(Long id, @Valid CardInfoCreateDto cardCreateDto) {
    CardInfo card = cardMapper.toEntity(cardCreateDto);
    CardInfo updated = cardService.update(id, card);
    return ResponseEntity.ok(cardMapper.toDto(updated));
  }

  @Override
  public ResponseEntity<Void> delete(Long id) {
    cardService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
