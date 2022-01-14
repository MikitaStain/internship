package by.innowise.internship.service.impl;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.entity.Email;
import by.innowise.internship.exceptions.NoDataFoundException;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.EmailMapper;
import by.innowise.internship.repository.dao.EmailRepository;
import by.innowise.internship.service.UserEmailService;
import by.innowise.internship.service.UserService;
import by.innowise.internship.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserEmailServiceImpl implements UserEmailService {

    private final UserService userService;
    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;
    private final Validation validation;

    @Autowired
    public UserEmailServiceImpl(UserService userService,
                                EmailMapper emailMapper,
                                EmailRepository emailRepository,
                                Validation validation) {
        this.userService = userService;
        this.emailMapper = emailMapper;
        this.emailRepository = emailRepository;
        this.validation = validation;
    }


    @Override
    public EmailDtoResponse addEmailForUser(Long userId, EmailDto emailDto) {


        validation.checkEmailValid(emailDto.getEmail());
        validation.checkDuplicateParameter(getEmailsForUser(userId), emailDto.getEmail());

        Email email = emailMapper.toEntity(emailDto);

        email.setUser(userService.getUser(userId));

        Optional.of(emailMapper.toEntity(emailDto))
                .ifPresent(email1 -> email.setUser(userService.getUser(userId)));

        return emailMapper.toEmailResponseDto(emailRepository.save(email));
    }

    private List<String> getEmailsForUser(Long userId) {

        return userService.getUser(userId).getEmails()
                .stream()
                .map(Email::getEmail)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmailDtoResponse> getAllEmailForUser(Long userId) {

        List<EmailDtoResponse> collect = userService.getUser(userId)
                .getEmails()
                .stream()
                .map(emailMapper::toEmailResponseDto)
                .collect(Collectors.toList());

        if (collect.isEmpty()) {

            throw new NoDataFoundException("For user not email");
        }

        return collect;
    }

    @Override
    public EmailDtoResponse updateEmailForUser(Long userId, Long emailId, EmailDto emailDto) {

        validation.checkEmailValid(emailDto.getEmail());

        return userService.getUser(userId)
                .getEmails()
                .stream()
                .filter(email -> email.getId().equals(emailId))
                .findAny()
                .map(email -> renameEmailForUser(email, emailDto.getEmail()))
                .map(emailRepository::save)
                .map(emailMapper::toEmailResponseDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Email by id " + emailId + " for user not found")
                );
    }

    private Email renameEmailForUser(Email updateEmail, String emailName) {

        updateEmail.setEmail(emailName);

        return updateEmail;
    }


    @Override
    public void deleteEmailForUser(Long userId, Long emailId) {

        List<Email> emails = userService.getUser(userId).getEmails();

        if (emails.isEmpty()) {

            throw new ResourceNotFoundException("User dont have an email");
        }

        for (Email email : emails) {

            if (email.getId().equals(emailId)) {

                emailRepository.delete(email);

                return;
            }
        }
        throw new ResourceNotFoundException("Email by id for user not found");
    }
}
