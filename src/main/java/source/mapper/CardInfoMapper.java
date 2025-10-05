package source.mapper;

import org.mapstruct.*;
import source.dto.CardInfoCreateDto;
import source.dto.CardInfoDto;
import source.entity.CardInfo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {

  @Mapping(source = "user.id", target = "userId")
  CardInfoDto toDto(CardInfo card);

  @Mapping(source = "userId", target = "user.id")
  CardInfo toEntity(CardInfoDto dto);
  @Mapping(source = "userId", target = "user.id")
  CardInfo toEntity(CardInfoCreateDto dto);
  List<CardInfoDto> toDtoList(List<CardInfo> cards);
}

