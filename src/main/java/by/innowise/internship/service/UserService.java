package by.innowise.internship.service;

import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDtoResponse getUserById(Long id);

    void saveUser(UserCreateRequestDto userDto);

    void updateUser(UserDto userDto, Long id);

    void deleteUser(Long id);

    Page<UserDtoResponse> getAll(PagesDto pagesDto);

    User getUser(Long id);

    void addPosition(Long id, PositionDtoResponse positionDtoResponse);


}
