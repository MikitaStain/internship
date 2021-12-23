package by.innowise.internship.mappers;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto toDTO(Course course);

    Course toEntity(CourseDto courseDto);

}
