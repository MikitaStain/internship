package by.innowise.internship.service;

import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;

public interface ServiceUserAuth {

    UserDtoForAuthResponse getAuthenticationUser(String userName, String password);

    UserDtoForAuthResponse getUserByLogin(String userLogin);
}
