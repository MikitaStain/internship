package by.innowise.internship.mappers;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.CourseDto.CourseDtoBuilder;
import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.PositionDTO.PositionDTOBuilder;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.UserDto.UserDtoBuilder;
import by.innowise.internship.dto.responseDto.EmailDtoResponse;
import by.innowise.internship.dto.responseDto.EmailDtoResponse.EmailDtoResponseBuilder;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse.UserDtoResponseBuilder;
import by.innowise.internship.entity.Course;
import by.innowise.internship.entity.Email;
import by.innowise.internship.entity.Position;
import by.innowise.internship.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-24T13:38:54+0300",
    comments = "version: 1.5.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15.0.5 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.name( user.getName() );
        userDto.lastName( user.getLastName() );
        userDto.login( user.getLogin() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( userDto.getLogin() );
        user.setName( userDto.getName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }

    @Override
    public User create(UserCreateRequestDto userCreateRequestDto) {
        if ( userCreateRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( userCreateRequestDto.getLogin() );
        user.setPassword( userCreateRequestDto.getPassword() );

        return user;
    }

    @Override
    public UserDtoResponse toUserResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoResponseBuilder userDtoResponse = UserDtoResponse.builder();

        userDtoResponse.id( user.getId() );
        userDtoResponse.login( user.getLogin() );
        userDtoResponse.name( user.getName() );
        userDtoResponse.lastName( user.getLastName() );
        userDtoResponse.emails( emailListToEmailDtoResponseList( user.getEmails() ) );
        userDtoResponse.courses( courseListToCourseDtoList( user.getCourses() ) );
        userDtoResponse.position( positionToPositionDTO( user.getPosition() ) );

        return userDtoResponse.build();
    }

    protected EmailDtoResponse emailToEmailDtoResponse(Email email) {
        if ( email == null ) {
            return null;
        }

        EmailDtoResponseBuilder emailDtoResponse = EmailDtoResponse.builder();

        emailDtoResponse.id( email.getId() );
        emailDtoResponse.email( email.getEmail() );

        return emailDtoResponse.build();
    }

    protected List<EmailDtoResponse> emailListToEmailDtoResponseList(List<Email> list) {
        if ( list == null ) {
            return null;
        }

        List<EmailDtoResponse> list1 = new ArrayList<EmailDtoResponse>( list.size() );
        for ( Email email : list ) {
            list1.add( emailToEmailDtoResponse( email ) );
        }

        return list1;
    }

    protected CourseDto courseToCourseDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDtoBuilder courseDto = CourseDto.builder();

        courseDto.name( course.getName() );

        return courseDto.build();
    }

    protected List<CourseDto> courseListToCourseDtoList(List<Course> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseDto> list1 = new ArrayList<CourseDto>( list.size() );
        for ( Course course : list ) {
            list1.add( courseToCourseDto( course ) );
        }

        return list1;
    }

    protected PositionDTO positionToPositionDTO(Position position) {
        if ( position == null ) {
            return null;
        }

        PositionDTOBuilder positionDTO = PositionDTO.builder();

        positionDTO.name( position.getName() );

        return positionDTO.build();
    }
}
