package by.innowise.internship.service;

import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceUserAuthImpl implements ServiceUserAuth {


    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    private static final String URL_FIND_USER_BY_LOGIN;

    static {

        URL_FIND_USER_BY_LOGIN = "http://localhost:8080/api/users/login?";
    }

    @Autowired
    public ServiceUserAuthImpl(RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDtoForAuthResponse getAuthenticationUser(String userName, String password) {

        UserDtoForAuthResponse user = getUserByLogin(userName);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            return user;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login or password incorrect");

    }

    @Override
    public UserDtoForAuthResponse getUserByLogin(String userLogin) {

        return restTemplate.getForObject
                (URL_FIND_USER_BY_LOGIN + "login=" + userLogin, UserDtoForAuthResponse.class);
    }
}
