package by.innowise.internship.mappers.responseMapper;

import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseResponseMapper {

    CourseResponseMapper INSTANCE = Mappers.getMapper(CourseResponseMapper.class);

    CourseDtoResponse toDTO(Course course);

    Course toEntity(CourseDtoResponse courseDtoResponse);


}
