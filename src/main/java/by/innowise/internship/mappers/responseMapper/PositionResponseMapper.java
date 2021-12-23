package by.innowise.internship.mappers.responseMapper;

import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PositionResponseMapper {

    PositionResponseMapper INSTANCE = Mappers.getMapper(PositionResponseMapper.class);

    PositionDtoResponse toDTO(Position position);

    Position toEntity(PositionDtoResponse positionDtoResponse);

}
