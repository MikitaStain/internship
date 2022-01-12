package by.innowise.internship.service;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.entity.Course;

public interface CourseService {

    CourseDtoResponse getCourseById(Long id);

    Long saveCourse(CourseDto courseDto);

    CourseDtoResponse updateCourse(CourseDto courseDto, Long id);

    void deleteCourse(Long id);

    PagesDtoResponse<CourseDtoResponse> getAll(int size, int page, String sort);

    Course getCourse(Long id);
}
