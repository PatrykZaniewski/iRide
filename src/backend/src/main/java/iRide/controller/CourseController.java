package iRide.controller;

import iRide.service.Course.CourseService;
import iRide.service.Course.model.input.CourseInput;
import iRide.service.Course.model.output.CourseListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    public final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "")
    public String getCourses(Model model) {
        List<CourseListOutput> courseListOutputs = this.courseService.getCourseListOutput();
        model.addAttribute("courseListOutputs", courseListOutputs);
        return "courses";
    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
//        this.courseService.deleteCategory(id);
//        return new ResponseEntity<>(id, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/")
//    public ResponseEntity<Object> createOne(@RequestBody CourseInput courseInput) {
//        int id = this.courseService.createCourse(courseInput);
//        return ResponseEntity.ok(id);
//    }

    @ModelAttribute("accountRole")
    public String getAccountRole(Authentication authentication) {
        if (authentication != null) {
            return String.valueOf(authentication.getAuthorities().toArray()[0]);
        }
        return null;
    }
}
