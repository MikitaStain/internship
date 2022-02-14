package by.innowise.internship.mappers;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.PositionDTO.PositionDTOBuilder;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse.PositionDtoResponseBuilder;
import by.innowise.internship.entity.Position;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-24T13:38:54+0300",
    comments = "version: 1.5.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15.0.5 (Azul Systems, Inc.)"
)
@Component
public class PositionMapperImpl implements PositionMapper {

    @Override
    public PositionDTO toPositionDto(Position position) {
        if ( position == null ) {
            return null;
        }

        PositionDTOBuilder positionDTO = PositionDTO.builder();

        positionDTO.name( position.getName() );

        return positionDTO.build();
    }

    @Override
    public Position toPositionEntity(PositionDTO positionDTO) {
        if ( positionDTO == null ) {
            return null;
        }

        Position position = new Position();

        position.setName( positionDTO.getName() );

        return position;
    }

    @Override
    public PositionDtoResponse toPositionResponseDto(Position position) {
        if ( position == null ) {
            return null;
        }

        PositionDtoResponseBuilder positionDtoResponse = PositionDtoResponse.builder();

        positionDtoResponse.id( position.getId() );
        positionDtoResponse.name( position.getName() );

        return positionDtoResponse.build();
    }
}
