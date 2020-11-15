package iRide.controller;

import iRide.service.Course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        this.courseService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
