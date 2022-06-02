package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UpdateUserDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gateway/users")
@Api("gateway users")
public class UserRestController {

    private static final String USER_URL;
    private static final String URL_FOR_PAGINATION;
    private static final String URL_FOR_USERS_FILTER;

    static {
        USER_URL = "http://localhost:8080/api/users/";
        URL_FOR_PAGINATION = "http://localhost:8080/api/users";
        URL_FOR_USERS_FILTER = "http://localhost:8080/api/users/filter?";
    }

    private final RestTemplate restTemplate;

    @Autowired
    public UserRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping("/{id}")
    @ApiOperation("gateway method get for user")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable("id") Long id) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        UserDtoResponse userDtoResponse = restTemplate.getForObject(USER_URL + id, UserDtoResponse.class);

        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("gateway method delete for user")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        restTemplate.delete(USER_URL + id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}")
    @ApiOperation("gateway update user")
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UpdateUserDto updateUserDto,
                                                      @PathVariable("id") Long id) {

        HttpEntity<UpdateUserDto> updateUserDtoHttpEntity = new HttpEntity<>(updateUserDto);

        return restTemplate.
                exchange(USER_URL + id, HttpMethod.PUT, updateUserDtoHttpEntity, UserDtoResponse.class);
    }

    @GetMapping
    @ApiOperation("gateway get all users")
    public ResponseEntity<PagesDtoResponse<UserDtoResponse>> getAllUsers
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "name") String sort) {

        PagesDtoResponse<UserDtoResponse> users = restTemplate.getForObject
                (
                        URL_FOR_PAGINATION + "?size=" + size
                                + "&page=" + page
                                + "&sort=" + sort
                        , PagesDtoResponse.class
                );

        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    @GetMapping("/filter")
    @ApiOperation("gateway filter users")
    public ResponseEntity<PagesDtoResponse<UserDtoResponse>> getUsersByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userLogin,
            @RequestParam(required = false) String userLastName,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String course,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "name") String sort) {

        PagesDtoResponse<UserDtoResponse> users = restTemplate.getForObject
                (
                        getUrlForFilterUsers(userName, userLogin, userLastName, position, course, size, page, sort)
                        , PagesDtoResponse.class
                );

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private String getUrlForFilterUsers(
            String userName,
            String userLogin,
            String userLastName,
            String position,
            String course,
            int size,
            int page,
            String sort) {

        StringBuilder url = new StringBuilder(URL_FOR_USERS_FILTER);

        if (userName != null && !userName.isEmpty()) {
            url.append("&userName=")
                    .append(userName);
        }
        if (userLogin != null && !userLogin.isEmpty()) {
            url.append("&userLogin=")
                    .append(userLogin);
        }
        if (userLastName != null && !userLastName.isEmpty()) {
            url.append("&userLastName=")
                    .append(userLastName);
        }
        if (position != null && !position.isEmpty()) {
            url.append("&position=")
                    .append(position);
        }
        if (course != null && !course.isEmpty()) {
            url.append("&course=")
                    .append(course);
        }
        url.append("&size=")
                .append(size)
                .append("&page=")
                .append(page)
                .append("&sort=")
                .append(sort);
        return String.valueOf(url);
    }

}
