package iRide.controller;

import iRide.service.InstructorCategory.InstructorCategoryService;
import iRide.service.InstructorCategory.model.input.InstructorCategoryInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/instructorCategory")
public class InstructorCategoryController {

    private final InstructorCategoryService instructorCategoryService;

    @Autowired
    public InstructorCategoryController(InstructorCategoryService instructorCategoryService) {
        this.instructorCategoryService = instructorCategoryService;
    }

    @PostMapping("/{instructorId}")
    public ResponseEntity<Object> assign(@PathVariable int instructorId, @RequestBody List<InstructorCategoryInput> instructorCategoryInputs) {
        int result = this.instructorCategoryService.assignCategoriesToInstructor(instructorCategoryInputs, instructorId);
        return ResponseEntity.ok(result);
    }
}
