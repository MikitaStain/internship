package by.innowise.internship.controller;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.service.EmailService;
import by.innowise.internship.service.UserService;
import by.innowise.internship.service.impl.PagesService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users/{user_id}/emails")
@Api("Rest controller for emails")
public class EmailRestController {

    private final EmailService emailService;
    private final PagesService pagesService;
    private final UserService userService;


    @Autowired
    public EmailRestController(EmailService emailService, PagesService pagesService, UserService userService) {
        this.emailService = emailService;
        this.pagesService = pagesService;
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation("save an email")
    public ResponseEntity<EmailDto> addEmail(
            @PathVariable("user_id") Long id,
            @RequestBody EmailDto emailDto) {

        emailService.saveEmail(id, emailDto);

        throw new ResponseStatusException(HttpStatus.CREATED, "Email " + emailDto.getEmail() +
                " added successfully");
    }

    @GetMapping
    @ApiOperation("getting an email by id")
    public ResponseEntity<List<EmailDtoResponse>> getEmail(@PathVariable("user_id") Long id) {

        try {

            List<EmailDtoResponse> emailDto = emailService.getEmailDto(id);
            return new ResponseEntity<>(emailDto, HttpStatus.OK);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }

    @PutMapping("/{id}")
    @ApiOperation("update email by id")
    public ResponseEntity<HttpStatus> updateEmail(@RequestBody EmailDto emailDto,
                                                  @PathVariable("id") Long id,
                                                  @PathVariable("user_id") Long userId) {

        try {
            emailService.updateEmail(emailDto, id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email by id not found");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete email by id")
    public ResponseEntity<HttpStatus> deleteEmail(@PathVariable("id") Long id,
                                                  @PathVariable("user_id") Long userId) {


        try {
            emailService.deleteEmail(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email by id not found");
        }
    }

//    @GetMapping
//    @ApiOperation("get all emails")
//    public ResponseEntity<List<EmailDtoResponse>> getAllEmail(@PathVariable("user_id") Long userId) {
//
//        try {
//            UserDtoResponse userById = userService.getUserById(userId);
//
//            List<EmailDtoResponse> emails = userById.getEmails();
//
//            if (emails.isEmpty()) {
//
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "emails for "
//                        + userById.getLogin()
//                        + " is empty");
//            }
//
//            return new ResponseEntity<>(emails, HttpStatus.OK);
//
//        } catch (Exception e) {
//
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//
//        }
//
//
//    }
}
