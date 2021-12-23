package by.innowise.internship.controller;


import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.service.PositionService;
import by.innowise.internship.service.UserService;
import by.innowise.internship.service.impl.PagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api("User Rest Controller")
public class UserRestController {

    private final UserService userService;
    private final PagesService pagesService;
    private final PositionService positionService;

    @Autowired
    public UserRestController(UserService userService, PagesService pagesService, PositionService positionService) {
        this.userService = userService;
        this.pagesService = pagesService;
        this.positionService = positionService;
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a user by id")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable("id") Long id) {

        try {
            UserDtoResponse userById = userService.getUserById(id);

            return new ResponseEntity<>(userById, HttpStatus.OK);


        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User by id not found");
        }

    }

    @PostMapping
    @ApiOperation("save a user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserCreateRequestDto userDto) {

        userService.saveUser(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/")
    @ApiOperation("update a user by id")
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UserDto userDto,
                                                      @PathVariable("id") Long id) {

        try {
            userService.updateUser(userDto, id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User by id not found");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete user by id")
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long id) {

        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User by id not found");
        }
    }

    @GetMapping
    @ApiOperation("get all users")
    public ResponseEntity<PagesDtoResponse> getAllPosition(@RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "name") String name) {

        PagesDto pagesDto = pagesService.getPagesDto(size, page, name);

        List<UserDtoResponse> allUsers = userService
                .getAll(pagesDto)
                .getContent();

        if (allUsers.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "users is empty");
        }

        PagesDtoResponse pagesDtoResponse = pagesService.getPagesDtoResponse(pagesDto, allUsers);

        return new ResponseEntity<>(pagesDtoResponse, HttpStatus.OK);

    }

    @PatchMapping("/{id}/positions")
    @ApiOperation("add positions for user")
    public ResponseEntity<HttpStatus> addPositions(@PathVariable("id") Long id,
                                                   @RequestParam Long idPosition) {

        PositionDtoResponse positionById = positionService.getPositionById(idPosition);
        userService.addPosition(id, positionById);

        throw new ResponseStatusException(HttpStatus.OK);

    }
}
