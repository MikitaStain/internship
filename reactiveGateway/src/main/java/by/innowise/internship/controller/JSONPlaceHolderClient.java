package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UpdateUserDto;
import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    Flux<PagesDtoResponse> getAllUsers(@RequestParam(name = "size",defaultValue = "5") int size,
                                       @RequestParam(name = "page",defaultValue = "0") int page,
                                       @RequestParam(name = "sort",required = false, defaultValue = "name") String sort);

    @PostMapping
    Mono<Long> createUser(@RequestBody UserCreateRequestDto user);

    @DeleteMapping(value = "{id}")
    Mono<HttpStatus> deleteUserById(@PathVariable ("id") Long userId);

    @PutMapping(value = "{id}")
    Mono<UserDtoResponse> updateUser(@RequestBody UpdateUserDto userDto,
                                     @PathVariable("id") Long id);

    @GetMapping
    Mono<PagesDtoResponse<UserDtoResponse>> getUsersByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userLogin,
            @RequestParam(required = false) String userLastName,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String course,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "name") String sort);
}
