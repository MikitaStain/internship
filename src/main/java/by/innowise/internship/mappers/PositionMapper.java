package by.innowise.internship.mappers;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.entity.Position;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    Position toPositionEntity(PositionDTO positionDTO);

    PositionDtoResponse toPositionResponseDto(Position position);


}
