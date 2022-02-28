package by.innowise.internship.controller;


import by.innowise.internship.dto.UpdateUserDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("save a user")
    public ResponseEntity<Long> createUser(@RequestBody UserCreateRequestDto userDto) {

        Long idNewUser = userService.saveUser(userDto);

        return new ResponseEntity<>(idNewUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("update a user by id")
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UpdateUserDto userDto,
                                                      @PathVariable("id") Long id) {

        UserDtoResponse userDtoResponse = userService
                .updateUser(userDto, id, userDto.getPositionId(), userDto.getCourseId());

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
    public ResponseEntity<PagesDtoResponse<UserDtoResponse>> getAllUsers
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "name") String sort) {

        PagesDtoResponse<UserDtoResponse> users = userService.getAll(size, page, sort);


        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/filter")
    @ApiOperation("filter users")
    public ResponseEntity<PagesDtoResponse<UserDtoResponse>> getUsersByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userLogin,
            @RequestParam(required = false) String userLastName,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String course,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "name") String sort) {

        PagesDtoResponse<UserDtoResponse> usersByFilter = userService.getUsersByFilter(
                userName
                , userLogin
                , userLastName
                , position
                , course
                , size, page, sort);

        return new ResponseEntity<>(usersByFilter, HttpStatus.OK);
    }

    @GetMapping("/login")
    @ApiOperation("Find user by login")
    public ResponseEntity<UserDtoResponse> getUserByLogin(@RequestParam String login) {

        UserDtoResponse byLogin = userService.findByLogin(login);

        return new ResponseEntity<>(byLogin, HttpStatus.OK);
    }

    @GetMapping("/loginAndPassword")
    @ApiOperation("Find user by login and password")
    public ResponseEntity<UserDtoForAuthResponse> getUserByLoginAndPassword(@RequestParam String login,
                                                                            @RequestParam String password) {

        UserDtoForAuthResponse userByLoginAndPassword =
                userService.findByLoginAndPassword(login, password);

        return new ResponseEntity<>(userByLoginAndPassword, HttpStatus.OK);
    }
}
