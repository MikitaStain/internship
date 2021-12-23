package by.innowise.internship.service.impl;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.entity.Email;
import by.innowise.internship.mappers.EmailMapper;
import by.innowise.internship.mappers.responseMapper.EmailResponseMapper;
import by.innowise.internship.repository.dao.EmailRepository;
import by.innowise.internship.service.EmailService;
import by.innowise.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final EmailResponseMapper emailResponseMapper;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, EmailMapper emailMapper,
                            EmailResponseMapper emailResponseMapper, UserService userService) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
        this.emailResponseMapper = emailResponseMapper;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public EmailDtoResponse getEmailById(Long id) {

        return emailRepository.findById(id)
                .map(emailResponseMapper::toDTO)
                .orElseThrow();
    }


    @Override
    public void saveEmail(Long id, EmailDto emailDto) {

        Optional.ofNullable(emailDto)
                .map(emailMapper::toEntity)
                .map(it -> buildEmail(id, it))
                .map(emailRepository::save)
                .orElseThrow();
    }

    private Email buildEmail(Long id, Email email) {

        email.setUser(userService.getUser(id));

        return email;
    }

    @Override
    public void updateEmail(EmailDto emailDto, Long id) {

        Email emailById = getEmail(id);
        Email email = emailMapper.toEntity(emailDto);

        emailById.setEmail(email.getEmail());

        emailRepository.save(emailById);
    }

    @Override
    public void deleteEmail(Long id) {

        Email email = getEmail(id);

        emailRepository.delete(email);
    }

    @Override
    public Page<EmailDtoResponse> getAll(PagesDto pagesDto) {
        return emailRepository.findAll(PagesService.getPage(pagesDto))
                .map(emailResponseMapper::toDTO);
    }

    private Email getEmail(Long id) throws IllegalArgumentException {

        Email email = emailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("email by id not found"));

        return email;
    }

    @Override
    public List<EmailDtoResponse> getEmailDto(Long id) {

        List<EmailDtoResponse> emails = userService.getUserById(id).getEmails();

        if (emails.isEmpty()) {
            throw new IllegalArgumentException("Email for user does not exists");
        }

        return userService.getUserById(id).getEmails();
    }
}
