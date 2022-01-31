package by.innowise.internship.mappers;

import by.innowise.internship.dto.ImageDtoRequest;
import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    Image toEntity(ImageDtoRequest imageDtoRequest);

    ImageDtoResponse toDto(Image image);
}
