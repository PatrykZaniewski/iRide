package iRide.controller;

import iRide.service.Student.StudentService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createStudent(@RequestBody StudentCreateInput studentCreateInput){
        if (!studentCreateInput.checkDataCompleteness()){
            return new ResponseEntity<>("Incomplete request data.", HttpStatus.BAD_REQUEST);
        }
        try {
            int studentId = studentService.createStudent(studentCreateInput);
            return ResponseEntity.ok("Student account has been created. Student id = " + studentId);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
