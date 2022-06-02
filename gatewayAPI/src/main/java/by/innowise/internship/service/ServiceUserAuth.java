package by.innowise.internship.service;

import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;

public interface ServiceUserAuth {

    String getToken(String userName, String password);

    UserDtoForAuthResponse getUserByLogin(String userLogin);
}
