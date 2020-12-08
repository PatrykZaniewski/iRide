package iRide.controller;

import iRide.service.Instructor.model.output.InstructorAdminOutput;
import iRide.service.Student.StudentService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.service.Student.model.output.StudentAdminOutput;
import iRide.service.Student.model.output.StudentListOutput;
import iRide.service.Vehicle.model.output.VehicleListOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<StudentListOutput> studentListOutputs = this.studentService.getStudentListOutput();
        model.addAttribute("studentListOutputs", studentListOutputs);
        return "students";
    }

    @GetMapping(value = "/{id}")
    public String getStudentDetails(Model model, @PathVariable int id){
        try {
            StudentAdminOutput studentAdminOutput = this.studentService.getStudentDetailsAsAdmin(id);
            model.addAttribute("studentAdminOutput", studentAdminOutput);
            return "student_details_admin";
        }
        catch (NotFoundException e){
            return "redirect:/student?code=202";
        }
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
