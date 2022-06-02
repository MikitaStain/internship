package by.innowise.internship.service;

import by.innowise.internship.dto.requestDto.UserCreateRequestDto;

public interface ServiceUserRegistration {

    UserCreateRequestDto encodingUserPassword(UserCreateRequestDto user);
}
