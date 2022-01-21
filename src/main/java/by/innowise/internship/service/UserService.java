package by.innowise.internship.service;

import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;

import java.util.List;

public interface UserService {

    UserDtoResponse getUserById(Long id);

    Long saveUser(UserCreateRequestDto userDto);

    UserDtoResponse updateUser(UserDto userDto, Long id, Long positionId, Long courseId);

    void deleteUser(Long id);

    PagesDtoResponse<UserDtoResponse> getAll(int size, int page, String sort);

    User getUser(Long id);

    List<UserDtoResponse> getUsersByFilter(String userName,
                                String userLogin,
                                String userLastName,
                                String position,
                                String course,
                                int size,
                                int page,
                                String sort);
}
