package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.exception.MyErrorHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gateway")
@Api("gateway users")
public class UserRestController {

    private static final String GET_USER;

    static {
        GET_USER = "http://localhost:8080/api/users/";
    }


    @GetMapping("/{id}")
    @ApiOperation("gateway method get for user")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable("id") Long id) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new MyErrorHandler());
        UserDtoResponse userDtoResponse = restTemplate.getForObject(GET_USER + id, UserDtoResponse.class);

        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation("gateway method post for user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(GET_USER, userCreateRequestDto, UserCreateRequestDto.class);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("gateway method delete for user")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(GET_USER + id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

//    @PutMapping("/{id}")
//    @ApiOperation("gateway update user")
//    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UserDto userDto,
//                                                      @PathVariable("id") Long id
//                                                     ,@RequestParam(required = false) Long positionId,
//                                                      @RequestParam(required = false) Long courseId
//    ) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        UserDtoResponse updateUser = restTemplate.patchForObject(GET_USER + id, userDto, UserDtoResponse.class);
//
//        return new ResponseEntity<>(updateUser, HttpStatus.OK);
//    }
}
