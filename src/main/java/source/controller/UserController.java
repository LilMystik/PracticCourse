package source.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import source.controller.base.BaseController;
import source.dto.UserCreateDto;
import source.dto.UserDto;
import source.entity.User;
import source.exception.ResourceNotFoundException;
import source.mapper.UserMapper;
import source.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements BaseController<UserCreateDto, UserDto> {

  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
    User user = userMapper.toEntity(userCreateDto);
    User saved = userService.create(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(saved));
  }

  @Override
  public ResponseEntity<UserDto> getById(Long id) {
    User user = userService.getById(id);
    if (user == null) throw new ResourceNotFoundException("User not found with id " + id);
    return ResponseEntity.ok(userMapper.toDto(user));
  }

  @Override
  public ResponseEntity<List<UserDto>> getAllOrByIds(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return ResponseEntity.ok(userMapper.toDtoList(userService.getAll()));
    }
    return ResponseEntity.ok(userMapper.toDtoList(userService.getByIds(ids)));
  }

  @Override
  public ResponseEntity<UserDto> update(Long id, @Valid UserCreateDto userCreateDto) {
    User user = userMapper.toEntity(userCreateDto);
    User updated = userService.update(id, user);
    return ResponseEntity.ok(userMapper.toDto(updated));
  }

  @Override
  public ResponseEntity<Void> delete(Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
