package iRide.controller;

import iRide.model.Course;
import iRide.service.Course.CourseService;
import iRide.service.Course.model.input.CourseInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    public final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
        this.courseService.deleteCategory(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> createOne(@RequestBody CourseInput courseInput){
        int id = this.courseService.createCourse(courseInput);
        return ResponseEntity.ok(id);
    }
}
