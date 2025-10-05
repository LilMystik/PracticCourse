package source.controller.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<CreateDto, ReadDto> {

  @PostMapping
  ResponseEntity<ReadDto> create(@RequestBody CreateDto createDto);

  @GetMapping("/{id}")
  ResponseEntity<ReadDto> getById(@PathVariable Long id);

  @GetMapping
  ResponseEntity<List<ReadDto>> getAllOrByIds(@RequestParam(required = false) List<Long> ids);

  @PutMapping("/{id}")
  ResponseEntity<ReadDto> update(@PathVariable Long id, @RequestBody CreateDto createDto);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Long id);
}
