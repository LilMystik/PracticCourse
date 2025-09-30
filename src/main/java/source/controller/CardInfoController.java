package source.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import source.dto.CardInfoDto;
import source.entity.CardInfo;
import source.exception.ResourceNotFoundException;
import source.mapper.CardInfoMapper;
import source.service.CardInfoService;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardInfoController {

  private final CardInfoService cardService;
  private final CardInfoMapper cardMapper;

  @PostMapping
  public ResponseEntity<CardInfoDto> create(@Valid @RequestBody CardInfoDto cardDto) {
    CardInfo card = cardMapper.toEntity(cardDto);
    CardInfo saved = cardService.create(card);
    return ResponseEntity.status(HttpStatus.CREATED).body(cardMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CardInfoDto> getById(@PathVariable Long id) {
    CardInfo card = cardService.getById(id);
    if (card == null) throw new ResourceNotFoundException("Card not found with id " + id);
    return ResponseEntity.ok(cardMapper.toDto(card));
  }

  @GetMapping
  public ResponseEntity<List<CardInfoDto>> getByIds(@RequestParam(required = false) List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return ResponseEntity.ok(cardMapper.toDtoList(cardService.getAll()));
    }
    return ResponseEntity.ok(cardMapper.toDtoList(cardService.getByIds(ids)));
  }


  @PutMapping("/{id}")
  public ResponseEntity<CardInfoDto> update(@PathVariable Long id, @Valid @RequestBody CardInfoDto cardDto) {
    CardInfo card = cardMapper.toEntity(cardDto);
    CardInfo updated = cardService.update(id, card);
    return ResponseEntity.ok(cardMapper.toDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    cardService.delete(id);
    return ResponseEntity.noContent().build();
  }
}

