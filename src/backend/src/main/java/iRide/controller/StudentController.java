package iRide.controller;

import iRide.service.Student.StudentService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.service.Student.model.output.StudentListOutput;
import iRide.service.Vehicle.model.output.VehicleListOutput;
import iRide.utils.exception.DataExistsException;
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
//        model.addAttribute("test", studentListOutputs.get(1).getActiveCourses());
        return "students";
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
