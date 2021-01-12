package iRide.controller;

import iRide.config.AuthenticationUserDetails;
import iRide.service.Course.model.output.CourseAdminOutput;
import iRide.service.Course.model.output.CourseInstructorOutput;
import iRide.service.Course.model.output.CourseStudentOutput;
import iRide.service.Student.StudentService;
import iRide.service.Student.model.output.StudentAdminOutput;
import iRide.service.Student.model.output.StudentListAdminOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "")
    public String getStudents(Model model){
        List<StudentListAdminOutput> studentListAdminOutputs = this.studentService.getStudentListAdminOutput();
        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Student został usunięty pomyślnie.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Wybrany student został już usunięty.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Student o wybranym id nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }
        model.addAttribute("studentListAdminOutputs", studentListAdminOutputs);
        return "admin/students";
    }

    @GetMapping(value = "/{id}")
    public String getStudentDetails(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthenticationUserDetails authenticationUserDetails, @PathVariable int id){
        String group = authenticationUserDetails.getAuthorities().get(0).toString();
        int userId = authenticationUserDetails.getId();

        switch (group){
            case "ADMIN":
                try {
                    StudentAdminOutput studentAdminOutput = this.studentService.getStudentDetailsAsAdmin(id);
                    model.addAttribute("studentAdminOutput", studentAdminOutput);
                    return "admin/student_details_admin";
                }
                catch (NotFoundException e){
                    redirectAttributes.addFlashAttribute("code", 202);
                    return "redirect:/student";
                }
            case "INSTRUCTOR":
                return "instructor/student_details";
            default:
                //TODO do 403?
                return null;
        }
    }

    @DeleteMapping(value = "/{id}")
    public String deleteStudent(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            this.studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("code", 101);
        } catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/category";
    }





//    @PostMapping(value = "/")
//    public ResponseEntity<String> createStudent(@RequestBody StudentCreateInput studentCreateInput) {
//        try {
//            int studentId = studentService.createStudent(studentCreateInput);
//            return ResponseEntity.ok("Student account has been created. Student id = " + studentId);
//        } catch (DataExistsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }

}
