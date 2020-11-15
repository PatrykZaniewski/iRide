package iRide.controller;

import iRide.service.Student.StudentService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.utils.exception.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createStudent(@RequestBody StudentCreateInput studentCreateInput) {
        try {
            int studentId = studentService.createStudent(studentCreateInput);
            return ResponseEntity.ok("Student account has been created. Student id = " + studentId);
        } catch (DataExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
