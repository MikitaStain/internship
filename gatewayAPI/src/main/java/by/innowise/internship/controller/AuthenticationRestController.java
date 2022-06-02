package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import by.innowise.internship.security.JwtToken;
import by.innowise.internship.service.ServiceUserAuth;
import by.innowise.internship.service.ServiceUserRegistration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@Api("gateway auth controller")
public class AuthenticationRestController {

    private final RestTemplate restTemplate;
    private final ServiceUserAuth serviceUserAuth;
    private final ServiceUserRegistration serviceUserRegistration;
    private final JwtToken token;

    @Autowired
    public AuthenticationRestController(RestTemplate restTemplate,
                                        ServiceUserAuth serviceUserAuth,
                                        ServiceUserRegistration serviceUserRegistration, JwtToken token) {
        this.restTemplate = restTemplate;
        this.serviceUserAuth = serviceUserAuth;
        this.serviceUserRegistration = serviceUserRegistration;
        this.token = token;
    }

    private static final String URL_REGISTRATION = "http://localhost:8080/api/users/";

    @PostMapping("/logout")
    public void logout() {

        SecurityContextHolder.clearContext();
    }

    @PostMapping("/registration")
    @ApiOperation("registration user")
    public ResponseEntity<Long> registrationUsers(@RequestBody UserCreateRequestDto user) {

        Long userId = restTemplate
                .postForObject(URL_REGISTRATION, serviceUserRegistration.encodingUserPassword(user), Long.class);

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @GetMapping("/login")
    @ApiOperation("gateway login")
    public ResponseEntity<HttpStatus> getUserByLogin(@RequestParam String userName,
                                                     @RequestParam String password) {


        HttpHeaders responseHeader = new HttpHeaders();
        UserDtoForAuthResponse authenticationUser = serviceUserAuth.getAuthenticationUser(userName, password);
        responseHeader.set("Authorization",token.getJWTToken(authenticationUser.getLogin(),authenticationUser.getRole().getName()));


        return ResponseEntity.ok().headers(responseHeader).body(HttpStatus.OK);
    }
}
