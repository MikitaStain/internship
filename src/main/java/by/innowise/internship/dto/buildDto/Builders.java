package by.innowise.internship.dto.buildDto;

import by.innowise.internship.dto.UserDtoForFilter;
import org.springframework.stereotype.Component;

@Component
public class Builders {

    public UserDtoForFilter buildUserDtoForFilter(String userName,
                                                  String userLogin,
                                                  String userLastName,
                                                  String position,
                                                  String course){

        return UserDtoForFilter.builder()
                .name(userName)
                .login(userLogin)
                .lastName(userLastName)
                .positions(position)
                .courses(course)
                .build();
    }
}
