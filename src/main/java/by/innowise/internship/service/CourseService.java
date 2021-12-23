package by.innowise.internship.service;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {

    CourseDtoResponse getCourseById(Long id);

    void saveCourse(CourseDto courseDto);

    void updateCourse(CourseDto courseDto, Long id);

    void deleteCourse(Long id);

    Page<CourseDtoResponse> getAll(PagesDto pagesDto);
}
