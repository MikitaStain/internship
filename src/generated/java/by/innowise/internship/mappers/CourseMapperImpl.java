package by.innowise.internship.mappers;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.CourseDto.CourseDtoBuilder;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.dto.responseDto.CourseDtoResponse.CourseDtoResponseBuilder;
import by.innowise.internship.entity.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-24T13:38:53+0300",
    comments = "version: 1.5.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15.0.5 (Azul Systems, Inc.)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto toCourseDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDtoBuilder courseDto = CourseDto.builder();

        courseDto.name( course.getName() );

        return courseDto.build();
    }

    @Override
    public Course toEntity(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course course = new Course();

        course.setName( courseDto.getName() );

        return course;
    }

    @Override
    public CourseDtoResponse toCourseResponseDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDtoResponseBuilder courseDtoResponse = CourseDtoResponse.builder();

        courseDtoResponse.id( course.getId() );
        courseDtoResponse.name( course.getName() );

        return courseDtoResponse.build();
    }
}
