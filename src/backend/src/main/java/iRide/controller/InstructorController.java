package iRide.controller;

import iRide.service.Category.CategoryService;
import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/instructor")
public class InstructorController {
    private final InstructorService instructorService;
    private final CategoryService categoryService;

    @Autowired
    public InstructorController(InstructorService instructorService, CategoryService categoryService){
        this.instructorService = instructorService;
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createInstructor(@RequestBody InstructorCreateInput instructorCreateInput){
        if (!instructorCreateInput.checkDataCompleteness()){
            return new ResponseEntity<>("Incomplete request data.", HttpStatus.BAD_REQUEST);
        }
        try {
            int instructorId = instructorService.createInstructor(instructorCreateInput);
            return ResponseEntity.ok("Instructor account has been created. Student id = " + instructorId);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
