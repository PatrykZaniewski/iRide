package iRide.controller;

import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.utils.exception.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/instructor")
@Validated
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createInstructor(@Valid @RequestBody InstructorCreateInput instructorCreateInput) {
//        if (!instructorCreateInput.checkDataCompleteness()){
//            return new ResponseEntity<>("Incomplete request data.", HttpStatus.BAD_REQUEST);
//        }
        try {
            int instructorId = instructorService.createInstructor(instructorCreateInput);
            return ResponseEntity.ok("Instructor account has been created. Student id = " + instructorId);
        } catch (DataExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable int id) {
        this.instructorService.deleteInstructor(id);
        return ResponseEntity.ok("");
    }
}
