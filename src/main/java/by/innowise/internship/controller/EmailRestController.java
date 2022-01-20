package by.innowise.internship.controller;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.service.UserEmailService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{user_id}/emails")
@Api("Rest controller for emails")
public class EmailRestController {

    private final UserEmailService userEmailService;


    @Autowired
    public EmailRestController(UserEmailService userEmailService) {
        this.userEmailService = userEmailService;
    }

    @PostMapping
    @ApiOperation("save an email")

    public ResponseEntity<EmailDtoResponse> addEmail(@PathVariable("user_id") Long id,
                                                     @RequestBody EmailDto emailDto) {

        EmailDtoResponse email = userEmailService.addEmailForUser(id, emailDto);

        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }


    @GetMapping
    @ApiOperation("getting an email by id")
    public ResponseEntity<List<EmailDtoResponse>> getEmail(@PathVariable("user_id") Long id) {

        List<EmailDtoResponse> emails = userEmailService.getAllEmailForUser(id);

        return new ResponseEntity<>(emails, HttpStatus.OK);
    }


    @PutMapping("/{email_id}")
    @ApiOperation("update email by id")
    public ResponseEntity<EmailDtoResponse> updateEmail(@RequestBody EmailDto emailDto,
                                                        @PathVariable("email_id") Long emailId,
                                                        @PathVariable("user_id") Long userId) {

        EmailDtoResponse email = userEmailService.updateEmailForUser(userId, emailId, emailDto);

        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @DeleteMapping("/{email_id}")
    @ApiOperation("delete email by id")
    public ResponseEntity<HttpStatus> deleteEmail(@PathVariable("email_id") Long emailId,
                                                  @PathVariable("user_id") Long userId) {

        userEmailService.deleteEmailForUser(userId, emailId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
