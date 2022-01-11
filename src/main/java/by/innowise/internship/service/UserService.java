package by.innowise.internship.service;

import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;

public interface UserService {

    UserDtoResponse getUserById(Long id);

    Long saveUser(UserCreateRequestDto userDto);

    UserDtoResponse updateUser(UserDto userDto, Long id);

    void deleteUser(Long id);

    PagesDtoResponse<UserDtoResponse> getAll(int size, int page, String sort);

    User getUser(Long id);

    void addPosition(Long id, Long idPosition);


}
