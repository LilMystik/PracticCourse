package source.mapper;

import org.mapstruct.*;
import source.dto.UserDto;
import source.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
  User toEntity(UserDto userDto);

  List<UserDto> toDtoList(List<User> users);
}
