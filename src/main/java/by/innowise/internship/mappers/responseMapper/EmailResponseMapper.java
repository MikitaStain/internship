package by.innowise.internship.mappers.responseMapper;

import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmailResponseMapper {

    EmailResponseMapper INSTANCE = Mappers.getMapper(EmailResponseMapper.class);

    EmailDtoResponse toDTO(Email email);

    Email toEntity(EmailDtoResponse emailDtoResponse);
}
