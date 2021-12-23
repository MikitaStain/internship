package by.innowise.internship.service.impl;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.entity.Course;
import by.innowise.internship.mappers.CourseMapper;
import by.innowise.internship.mappers.responseMapper.CourseResponseMapper;
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
    private final CourseResponseMapper courseResponseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             CourseMapper courseMapper,
                             CourseResponseMapper courseResponseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseResponseMapper = courseResponseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDtoResponse getCourseById(Long id) {

        return courseRepository.findById(id)
                .map(courseResponseMapper::toDTO)
                .orElseThrow();
    }


    @Override
    public void saveCourse(CourseDto courseDto) {

        Optional.ofNullable(courseDto)
                .map(courseMapper::toEntity)
                .map(courseRepository::save)
                .orElseThrow();
    }

    @Override
    public void updateCourse(CourseDto courseDto, Long id) {

        Course courseById = getCourse(id);
        Course course = courseMapper.toEntity(courseDto);

        courseById.setName(course.getName());
//        courseById.setUsers(course.getUsers());

        courseRepository.save(courseById);
    }

    @Override
    public void deleteCourse(Long id) {

        Course course = getCourse(id);

        courseRepository.delete(course);
    }

    @Override
    public Page<CourseDtoResponse> getAll(PagesDto pagesDto) {

        return courseRepository.findAll(PagesService.getPage(pagesDto))
                .map(courseResponseMapper::toDTO);

    }

    private Course getCourse(Long id) throws IllegalArgumentException {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("course by id not found"));

        return course;
    }


}
