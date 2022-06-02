package by.innowise.internship.service;

import by.innowise.internship.dto.UpdateUserDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;

public interface UserService extends UserGlobalService {

    UserDtoResponse getUserById(Long id);

    Long saveUser(UserCreateRequestDto userDto);

    UserDtoResponse updateUser(UpdateUserDto userDto, Long id, Long positionId, Long courseId);

    void deleteUser(Long id);

    PagesDtoResponse<UserDtoResponse> getAll(int size, int page, String sort);

    PagesDtoResponse<UserDtoResponse> getUsersByFilter(String userName,
                                                       String userLogin,
                                                       String userLastName,
                                                       String position,
                                                       String course,
                                                       int size,
                                                       int page,
                                                       String sort);

    UserDtoResponse findByLogin(String login);

    UserDtoForAuthResponse findByLoginAndPassword(String login, String password);
}
