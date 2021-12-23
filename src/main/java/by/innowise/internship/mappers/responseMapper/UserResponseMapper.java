package by.innowise.internship.mappers.responseMapper;

import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    UserDtoResponse toDTO(User user);

    User toEntity(UserDtoResponse userDtoResponse);
}
