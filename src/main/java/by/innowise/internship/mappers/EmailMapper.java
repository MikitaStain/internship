package by.innowise.internship.mappers;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    EmailDto toDTO(Email email);

    Email toEntity(EmailDto emailDto);

}
