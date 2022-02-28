package by.innowise.internship.service;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;

import java.util.List;

public interface UserEmailService {

    EmailDtoResponse addEmailForUser(Long userId, EmailDto emailDto);

    List<EmailDtoResponse> getAllEmailForUser(Long userId);

    EmailDtoResponse updateEmailForUser (Long userId, Long emailId, EmailDto emailDto);

    void deleteEmailForUser(Long userId, Long emailId);
}
