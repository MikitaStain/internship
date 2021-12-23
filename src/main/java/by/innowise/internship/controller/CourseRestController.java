package by.innowise.internship.controller;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.service.CourseService;
import by.innowise.internship.service.impl.PagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@Api(value = "course rest controller")
public class CourseRestController {

    private final CourseService courseService;
    private final PagesService pagesService;

    @Autowired
    public CourseRestController(CourseService courseService, PagesService pagesService) {
        this.courseService = courseService;
        this.pagesService = pagesService;
    }

    @PostMapping
    @ApiOperation("save a course")
    public ResponseEntity<HttpStatus> saveCourse(@RequestBody CourseDto courseDto) {

        courseService.saveCourse(courseDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a course by id")
    public ResponseEntity<CourseDtoResponse> getCourseByID(@PathVariable("id") Long id) {

        try {
            CourseDtoResponse courseById = courseService.getCourseById(id);

            return new ResponseEntity<>(courseById, HttpStatus.OK);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course by id not found");
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("update a course by id")
    public ResponseEntity<HttpStatus> updateCourse(@RequestBody CourseDto courseDto,
                                                   @PathVariable("id") Long id) {

        try {
            courseService.updateCourse(courseDto, id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course by id not found");
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("delete a course by id")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") Long id) {

        try {
            courseService.deleteCourse(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course by id not found");
        }
    }

    @GetMapping
    @ApiOperation("get all courses")
    public ResponseEntity<PagesDtoResponse> getAllPosition(@RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(
                                                                   required = false, defaultValue = "name"
                                                           ) String name) {

        PagesDto pagesDto = pagesService.getPagesDto(size, page, name);

        List<CourseDtoResponse> allCourses = courseService
                .getAll(pagesDto)
                .getContent();

        if (allCourses.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "courses is empty");
        }

        PagesDtoResponse pagesDtoResponse = pagesService.getPagesDtoResponse(pagesDto, allCourses);

        return new ResponseEntity<>(pagesDtoResponse, HttpStatus.OK);
    }
}
