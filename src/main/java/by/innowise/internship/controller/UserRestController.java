package by.innowise.internship.controller;


import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api("User Rest Controller")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a user by id")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable("id") Long id) {

        UserDtoResponse userById = userService.getUserById(id);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("save a user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserCreateRequestDto userDto) {

        userService.saveUser(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("update a user by id")
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UserDto userDto,
                                                      @PathVariable("id") Long id,
                                                      @RequestParam(required = false) Long positionId,
                                                      @RequestParam(required = false) Long courseId) {

        UserDtoResponse userDtoResponse = userService.updateUser(userDto, id, positionId, courseId);

        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete user by id")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @ApiOperation("get all users")
    public ResponseEntity<PagesDtoResponse<UserDtoResponse>> getAllPosition
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "name") String name) {

        PagesDtoResponse<UserDtoResponse> users = userService.getAll(size, page, name);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/filter")
    @ApiOperation("filter users")
    public ResponseEntity<List<UserDtoResponse>> getUsersByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userLogin,
            @RequestParam(required = false) String userLastName,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String course,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "name") String sort) {


        List<UserDtoResponse> usersByFilter = userService.getUsersByFilter(
                userName
                ,userLogin
                , userLastName
                , position
                , course, size, page, sort);

        System.out.println(usersByFilter);


        return new ResponseEntity<>(usersByFilter, HttpStatus.OK);
    }


}
