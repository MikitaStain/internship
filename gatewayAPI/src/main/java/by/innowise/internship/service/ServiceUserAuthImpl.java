package by.innowise.internship.service;

import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import by.innowise.internship.security.JWTToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceUserAuthImpl implements ServiceUserAuth{

    private final JWTToken token;
    private final RestTemplate restTemplate;

    private static final String URL;
    private static final String URL_FIND_USER_BY_LOGIN;

    static {

        URL = "http://localhost:8080/api/users/loginAndPassword?";
        URL_FIND_USER_BY_LOGIN = "http://localhost:8080/api/users/login";
    }

    public ServiceUserAuthImpl(JWTToken token, RestTemplate restTemplate) {
        this.token = token;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getToken(String userName, String password) {

        UserDtoForAuthResponse user = restTemplate
                .getForObject
                        (URL + "login=" + userName + "&password=" + password
                                , UserDtoForAuthResponse.class);

        if (user.getLogin() != null) {

            return token.getJWTToken(user.getLogin(), user.getRole().getName());
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login or password incorrect");
    }

    @Override
    public UserDtoForAuthResponse getUserByLogin(String userLogin){

        return restTemplate.getForObject(URL_FIND_USER_BY_LOGIN+ "?login=" + userLogin, UserDtoForAuthResponse.class);
    }
}
