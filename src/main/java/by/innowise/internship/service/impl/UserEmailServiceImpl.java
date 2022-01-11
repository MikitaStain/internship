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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserEmailServiceImpl implements UserEmailService {

    private final UserService userService;
    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;

    @Autowired
    public UserEmailServiceImpl(UserService userService,
                                EmailMapper emailMapper,
                                EmailRepository emailRepository) {
        this.userService = userService;
        this.emailMapper = emailMapper;
        this.emailRepository = emailRepository;
    }


    @Override
    public EmailDtoResponse addEmailForUser(Long userId, EmailDto emailDto) {

        Email email = emailMapper.toEntity(emailDto);

        email.setUser(userService.getUser(userId));

        return emailMapper.toEmailResponseDto(emailRepository.save(email));
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

        boolean flag = true;

        if (emails.isEmpty()) {

            throw new ResourceNotFoundException("User dont have an email");
        }

        for (Email email : emails) {

            if (email.getId().equals(emailId) && flag) {

                emailRepository.delete(email);

                flag = false;
            }
        }
        if (flag) {

            throw new ResourceNotFoundException("Email by id for user not found");

        }
    }
}
