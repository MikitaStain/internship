package by.innowise.internship.mappers;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    PositionDTO toDTO(Position position);

    Position toEntity(PositionDTO positionDTO);

}
