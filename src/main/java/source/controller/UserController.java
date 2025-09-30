package source.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import source.dto.UserDto;
import source.entity.User;
import source.exception.ResourceNotFoundException;
import source.mapper.UserMapper;
import source.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping
  public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User saved = userService.create(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable Long id) {
    User user = userService.getById(id);
    if (user == null) throw new ResourceNotFoundException("User not found with id " + id);
    return ResponseEntity.ok(userMapper.toDto(user));
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getByIds(@RequestParam(required = false) List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return ResponseEntity.ok(userMapper.toDtoList(userService.getAll()));
    }
    return ResponseEntity.ok(userMapper.toDtoList(userService.getByIds(ids)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User updated = userService.update(id, user);
    return ResponseEntity.ok(userMapper.toDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
