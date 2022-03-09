package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UpdateUserDto;
import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(
        name = "JSONPlaceHolderClient",
        url = "http://localhost:8080/api/users/"
)
public interface JSONPlaceHolderClient {

    @GetMapping(value = "{id}")
    Mono<UserDtoResponse> getUser(@PathVariable("id") Long userId);

    @GetMapping
    Flux<PagesDtoResponse> getAllUsers(@RequestParam(name = "size", defaultValue = "5") int size,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "sort", required = false, defaultValue = "name") String sort);

    @PostMapping
    Mono<Long> createUser(@RequestBody UserCreateRequestDto user);

    @DeleteMapping(value = "{id}")
    Mono<HttpStatus> deleteUserById(@PathVariable("id") Long userId);

    @PutMapping(value = "{id}")
    Mono<UserDtoResponse> updateUser(@RequestBody UpdateUserDto userDto,
                                     @PathVariable("id") Long id);

    @GetMapping
    Mono<PagesDtoResponse<UserDtoResponse>> getUsersByFilter(
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "userLogin", required = false) String userLogin,
            @RequestParam(name = "userLastName", required = false) String userLastName,
            @RequestParam(name = "position", required = false) String position,
            @RequestParam(name = "course", required = false) String course,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort);
}
