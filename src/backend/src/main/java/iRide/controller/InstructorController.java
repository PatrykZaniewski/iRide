package iRide.controller;

import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.output.InstructorAdminOutput;
import iRide.service.Instructor.model.output.InstructorListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping(value = "")
    public String getInstructors(Model model){
        List<InstructorListOutput> instructorListOutputs = this.instructorService.getInstructorListOutput();
        model.addAttribute("instructorListOutputs", instructorListOutputs);
        return "instructors";
    }

    @GetMapping(value = "/{id}")
    public String getInstructorDetails(Model model, @PathVariable int id){
        InstructorAdminOutput instructorAdminOutput = this.instructorService.getInstructorDetailsAsAdmin(id);
        model.addAttribute("instructorAdminOutput", instructorAdminOutput);
        return "instructor_details_admin";
    }

//    @PostMapping(value = "/")
//    public ResponseEntity<String> createInstructor(@Valid @RequestBody InstructorCreateInput instructorCreateInput) {
//        try {
//            int instructorId = instructorService.createInstructor(instructorCreateInput);
//            return ResponseEntity.ok("Instructor account has been created. Student id = " + instructorId);
//        } catch (DataExistsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable int id) {
        System.out.println("XDDD");
        return ResponseEntity.ok("");
    }

    @ModelAttribute("accountRole")
    public String getAccountRole(Authentication authentication){
        if (authentication != null){
            return String.valueOf(authentication.getAuthorities().toArray()[0]);
        }
        return null;
    }
}
