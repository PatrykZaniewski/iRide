package iRide.controller;

import iRide.service.Course.CourseService;
import iRide.service.Course.model.output.CourseAdminOutput;
import iRide.service.Course.model.output.CourseListAdminOutput;
import iRide.service.Course.model.output.CourseListStudentOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getCourses(Model model, Authentication authentication) {
        List<CourseListAdminOutput> courseListAdminOutputs = this.courseService.getCourseListAdminOutput();
        List<CourseListStudentOutput> courseListStudentOutputs = this.courseService.getCourseListStudentOutput(1);
        model.addAttribute("courseListStudentOutputs", courseListStudentOutputs);
        return "student/courses";
//        if (model.asMap().get("code") != null) {
//            Integer code = (Integer)model.asMap().get("code");
//            switch (code) {
//                case 101:
//                    model.addAttribute("infoMessage", "Kurs został usunięty pomyślnie.");
//                    break;
//                case 201:
//                    model.addAttribute("infoError", "Wybrany kurs został już usunięty.");
//                    break;
//                case 202:
//                    model.addAttribute("infoError", "Kurs o wybranym id nie istnieje.");
//                    break;
//                default:
//                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
//            }
//        }
//        model.addAttribute("courseListAdminOutputs", courseListAdminOutputs);
//        return "admin/courses";
    }

    @GetMapping(value = "/{id}")
    public String getCourseDetails(Model model, RedirectAttributes redirectAttributes, @PathVariable int id){
        try {
            CourseAdminOutput courseAdminOutput = this.courseService.getCourseDetailsAsAdmin(id);
            model.addAttribute("courseAdminOutput", courseAdminOutput);
            return "admin/course_details_admin";
        }
        catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 202);
            return "redirect:/course";
        }
    }

    @DeleteMapping(value = "/{id}")
    public String deleteCourse(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            this.courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("code", 101);
        } catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/course";
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
