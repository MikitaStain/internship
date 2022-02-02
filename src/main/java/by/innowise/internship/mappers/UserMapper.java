package by.innowise.internship.mappers;

import by.innowise.internship.dto.UpdateUserDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UpdateUserDto updateUserDto);

    User create(UserCreateRequestDto userCreateRequestDto);

    UserDtoResponse toUserResponseDto(User user);

}
