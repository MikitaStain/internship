package by.innowise.internship.mappers;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseDto courseDto);

    CourseDtoResponse toCourseResponseDto(Course course);

}
