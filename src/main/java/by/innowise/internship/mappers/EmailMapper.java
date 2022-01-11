package by.innowise.internship.mappers;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.entity.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailDto toEmailDto(Email email);

    Email toEntity(EmailDto emailDto);

    EmailDtoResponse toEmailResponseDto(Email email);


}
