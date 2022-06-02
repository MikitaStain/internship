package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UpdateUserDto;
import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
@Api("reactive gateway")
@Slf4j
public class UserRestControllerReactive {

    Logger logger = LoggerFactory.getLogger(UserRestControllerReactive.class);

    private static final String USER_URL;

    static {
        USER_URL = "localhost:8080/api/users/";
    }

    private final WebClient webClient;
    private final JSONPlaceHolderClient jsonPlaceHolderClient;

    @Autowired
    public UserRestControllerReactive(WebClient webClient, JSONPlaceHolderClient jsonPlaceHolderClient) {
        this.webClient = webClient;
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
    }

    @GetMapping("/{id}")
    @ApiOperation("React get user")
    public Mono<UserDtoResponse> getUserById(@PathVariable("id") Long userId) {

        return webClient.get()
                .uri(USER_URL + userId)
                .retrieve()
                .bodyToMono(UserDtoResponse.class);
    }

    @GetMapping()
    @ApiOperation("React get all users")
    public Flux<PagesDtoResponse> getAllUsers(@RequestParam(defaultValue = "5") int size,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "name") String sort) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_URL)
                        .queryParam("size", size)
                        .queryParam("page", page)
                        .queryParam("sort", sort)
                        .build())
                .retrieve()
                .bodyToFlux(PagesDtoResponse.class);

    }

    @PostMapping
    @ApiOperation("React add user")
    public Mono<Long> addUser(@RequestBody UserCreateRequestDto user) {

        return jsonPlaceHolderClient.createUser(user)
                .doOnSuccess(result -> logger.info("addUser() Success"))
                .doOnError(error -> logger.error("addUser() Error", error));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("React delete user")
    public Mono<HttpStatus> deleteUser(@PathVariable("id") Long userId) {

        return jsonPlaceHolderClient.deleteUserById(userId)
                .doOnSuccess(result -> logger.info("deleteUser() Success"))
                .doOnError(error -> logger.error("deleteUser() Error", error));
    }


    @PutMapping("/{id}")
    @ApiOperation("React update user")
    public Mono<UserDtoResponse> updateUser(@RequestBody UpdateUserDto userDto,
                                            @PathVariable("id") Long id) {

        return jsonPlaceHolderClient.updateUser(userDto, id)
                .doOnSuccess(result -> logger.info("updateUser() Success"))
                .doOnError(error -> logger.error("updateUser() Error", error));
    }

    @GetMapping("/filter")
    @ApiOperation("React filter users")
    public Mono<PagesDtoResponse<UserDtoResponse>> getUsersByFilter(
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "userLogin", required = false) String userLogin,
            @RequestParam(name = "userLastName", required = false) String userLastName,
            @RequestParam(name = "position", required = false) String position,
            @RequestParam(name = "course", required = false) String course,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort) {

        return jsonPlaceHolderClient.getUsersByFilter(userName, userLogin, userLastName, position, course, size, page, sort)
                .doOnSuccess(result -> logger.info("getUsersByFilter() Success"))
                .doOnError(error -> logger.info("getUsersByFilter() Error", error));
    }
}
