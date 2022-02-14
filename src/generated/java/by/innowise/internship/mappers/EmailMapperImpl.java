package by.innowise.internship.mappers;

import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.EmailDto.EmailDtoBuilder;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.dto.responseDto.EmailDtoResponse.EmailDtoResponseBuilder;
import by.innowise.internship.entity.Email;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-24T13:38:54+0300",
    comments = "version: 1.5.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15.0.5 (Azul Systems, Inc.)"
)
@Component
public class EmailMapperImpl implements EmailMapper {

    @Override
    public EmailDto toEmailDto(Email email) {
        if ( email == null ) {
            return null;
        }

        EmailDtoBuilder emailDto = EmailDto.builder();

        emailDto.email( email.getEmail() );

        return emailDto.build();
    }

    @Override
    public Email toEntity(EmailDto emailDto) {
        if ( emailDto == null ) {
            return null;
        }

        Email email = new Email();

        email.setEmail( emailDto.getEmail() );

        return email;
    }

    @Override
    public EmailDtoResponse toEmailResponseDto(Email email) {
        if ( email == null ) {
            return null;
        }

        EmailDtoResponseBuilder emailDtoResponse = EmailDtoResponse.builder();

        emailDtoResponse.id( email.getId() );
        emailDtoResponse.email( email.getEmail() );

        return emailDtoResponse.build();
    }
}
