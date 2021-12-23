package by.innowise.internship.service;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmailService {

    EmailDtoResponse getEmailById(Long id);

    void saveEmail(Long id, EmailDto emailDto);

    void updateEmail(EmailDto emailDto, Long id);

    void deleteEmail(Long id);

    Page<EmailDtoResponse> getAll(PagesDto pagesDto);

    List<EmailDtoResponse> getEmailDto(Long id);

}
