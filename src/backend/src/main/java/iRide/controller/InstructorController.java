package iRide.controller;

import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.output.InstructorAdminOutput;
import iRide.service.Instructor.model.output.InstructorListOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public String getInstructors(Model model, @RequestParam(required = false) Integer code, @RequestHeader(required = false, name = "test") String header){
        List<InstructorListOutput> instructorListOutputs = this.instructorService.getInstructorListOutput();
        if (code != null) {
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Instruktor został usunięty pomyślnie.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Wybrany instruktor został już usunięty.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Instruktor o wybranym id nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }
        model.addAttribute("instructorListOutputs", instructorListOutputs);
        return "instructors";
    }

    @GetMapping(value = "/{id}")
    public String getInstructorDetails(Model model, @PathVariable int id){
        try {
            InstructorAdminOutput instructorAdminOutput = this.instructorService.getInstructorDetailsAsAdmin(id);
            model.addAttribute("instructorAdminOutput", instructorAdminOutput);
            return "instructor_details_admin";
        }
        catch (NotFoundException e){
            return "redirect:/instructor?code=202";
        }
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
    public String deleteInstructor(@PathVariable int id) {
        int code = 101;
        try {
            this.instructorService.deleteInstructor(id);
        } catch (NotFoundException e){
            code = 201;
        }
        return "redirect:/instructor" + "?code=" + code;
    }

    @PostMapping(value = "")
    public String createInstructor(){
        return null;
    }

    @PutMapping(value = "")
    public String updateInstructor(){
        return null;
    }

    @ModelAttribute("accountRole")
    public String getAccountRole(Authentication authentication){
        if (authentication != null){
            return String.valueOf(authentication.getAuthorities().toArray()[0]);
        }
        return null;
    }
}
