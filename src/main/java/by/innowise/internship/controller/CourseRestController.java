package by.innowise.internship.controller;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.responseDto.CourseDtoResponse;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@Api(value = "course rest controller")
public class CourseRestController {

    private final CourseService courseService;

    @Autowired
    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ApiOperation("save a course")
    public ResponseEntity<Long> saveCourse(@RequestBody CourseDto courseDto) {

        Long id = courseService.saveCourse(courseDto);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a course by id")
    public ResponseEntity<CourseDtoResponse> getCourseByID(@PathVariable("id") Long id) {

        CourseDtoResponse courseById = courseService.getCourseById(id);

        return new ResponseEntity<>(courseById, HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    @ApiOperation("update a course by id")
    public ResponseEntity<CourseDtoResponse> updateCourse(@RequestBody CourseDto courseDto,
                                                          @PathVariable("id") Long id) {

        CourseDtoResponse course = courseService.updateCourse(courseDto, id);

        return new ResponseEntity<>(course, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    @ApiOperation("delete a course by id")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") Long id) {

        courseService.deleteCourse(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping
    @ApiOperation("get all courses")
    public ResponseEntity<PagesDtoResponse<CourseDtoResponse>> getAllPosition
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false,
                     defaultValue = "name") String name) {

        PagesDtoResponse<CourseDtoResponse> allCourses = courseService.getAll(size, page, name);

        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }
}
