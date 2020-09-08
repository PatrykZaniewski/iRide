package iRide.controller;

import iRide.service.Student.StudentService;
import iRide.service.Student.model.StudentCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createStudent(@RequestBody StudentCreateInput studentCreateInput){
        if (!studentCreateInput.checkDataCompleteness()){
            return new ResponseEntity<>("Incomplete request data.", HttpStatus.BAD_REQUEST);
        }
        studentService.createStudent(studentCreateInput);
        return ResponseEntity.ok("git");
    }
}
