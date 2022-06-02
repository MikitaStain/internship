package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import by.innowise.internship.service.ServiceUserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/gateway")
@Api("gateway auth controller")
public class AuthenticationRestController {

    private final RestTemplate restTemplate;
    private final ServiceUserAuth serviceUserAuth;

    @Autowired
    public AuthenticationRestController(RestTemplate restTemplate, ServiceUserAuth serviceUserAuth) {
        this.restTemplate = restTemplate;
        this.serviceUserAuth = serviceUserAuth;
    }

    private static final String URL_REGISTRATION;

    static {
        URL_REGISTRATION = "http://localhost:8080/api/users/";
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/registration")
    @ApiOperation("registration user")
    public ResponseEntity<Long> registrationUsers(@RequestBody UserCreateRequestDto user) {

        Long userId = restTemplate.postForObject(URL_REGISTRATION, user, Long.class);

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @GetMapping("/login")
    @ApiOperation("gateway login")
    public ResponseEntity<HttpStatus> getUserByLogin(@RequestParam String userName,
                                                     @RequestParam String password) {

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("Authentication", serviceUserAuth.getToken(userName, password));

        return ResponseEntity.ok().headers(responseHeader).body(HttpStatus.OK);
    }
}
