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
import by.innowise.internship.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final PagesService pagesService;
    private final Validation validation;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             CourseMapper courseMapper,
                             PagesService pagesService,
                             Validation validation) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.pagesService = pagesService;
        this.validation = validation;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDtoResponse getCourseById(Long id) {

        return courseMapper.toCourseResponseDto(getCourse(id));
    }


    @Override
    public Long saveCourse(CourseDto courseDto) {

        validation.checkDuplicateParameter(coursesName(), courseDto.getName());
        validation.checkParameter(courseDto.getName());

        return Optional.of(courseDto)
                .map(courseMapper::toEntity)
                .map(courseRepository::save)
                .map(Course::getId)
                .orElseThrow(() -> new NoCreateException("Course not created"));
    }

    private List<String> coursesName() {

        return courseRepository.findAll()
                .stream()
                .map(Course::getName)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDtoResponse updateCourse(CourseDto courseDto, Long id) {

        validation.checkParameter(courseDto.getName());
        validation.checkDuplicateParameter(coursesName(), courseDto.getName());

        Course courseById = getCourse(id);

        courseById.setName(courseDto.getName());

        return courseMapper.toCourseResponseDto(courseRepository.save(courseById));
    }

    @Override
    public void deleteCourse(Long id) {

        courseRepository.delete(getCourse(id));
    }

    @Override
    public Course getCourse(Long id) {

        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("course by id" + id + " not found"));
    }


    @Override
    @Transactional(readOnly = true)
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
