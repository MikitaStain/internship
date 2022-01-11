package by.innowise.internship.service.impl;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.entity.Course;
import by.innowise.internship.exceptions.NoCreateException;
import by.innowise.internship.exceptions.NoDataFoundException;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.CourseMapper;
import by.innowise.internship.repository.dao.CourseRepository;
import by.innowise.internship.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final PagesService pagesService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             CourseMapper courseMapper, PagesService pagesService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.pagesService = pagesService;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDtoResponse getCourseById(Long id) {

        return courseMapper.toCourseResponseDto(getCourse(id));
    }


    @Override
    public Long saveCourse(CourseDto courseDto) {

        return Optional.ofNullable(courseDto)
                .map(courseMapper::toEntity)
                .map(courseRepository::save)
                .map(Course::getId)
                .orElseThrow(() -> new NoCreateException("Course not created"));
    }

    @Override
    public CourseDtoResponse updateCourse(CourseDto courseDto, Long id) {

        Course courseById = getCourse(id);
        Course course = courseMapper.toEntity(courseDto);

        if (courseDto.getName().isBlank()) {

            courseById.setName(course.getName());
        }
        return courseMapper.toCourseResponseDto(courseRepository.save(courseById));
    }

    @Override
    public void deleteCourse(Long id) {

        Course course = getCourse(id);

        courseRepository.delete(course);
    }

    private Course getCourse(Long id) throws IllegalArgumentException {

        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("course by id not found"));
    }


    @Override
    public PagesDtoResponse<CourseDtoResponse> getAll(int size, int page, String sort) {

        Page<CourseDtoResponse> allPositions = courseRepository
                .findAll(pagesService.getPage(size, page, sort))
                .map(courseMapper::toCourseResponseDto);

        if (allPositions.isEmpty()) {

            throw new NoDataFoundException("Courses not found");
        }
        return pagesService.getPagesDtoResponse(size, page, sort, allPositions.getContent());


    }


}
