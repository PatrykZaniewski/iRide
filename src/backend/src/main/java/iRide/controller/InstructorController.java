package iRide.controller;

import iRide.config.AuthenticationUserDetails;
import iRide.service.Course.model.output.CourseAdminOutput;
import iRide.service.Course.model.output.CourseInstructorOutput;
import iRide.service.Course.model.output.CourseStudentOutput;
import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.output.InstructorAdminOutput;
import iRide.service.Instructor.model.output.InstructorListAdminOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<InstructorListAdminOutput> instructorListAdminOutputs = this.instructorService.getInstructorListAdminOutput();
        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
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
        model.addAttribute("instructorListAdminOutputs", instructorListAdminOutputs);
        return "admin/instructors";
    }

    @GetMapping(value = "/{id}")
    public String getInstructorDetails(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthenticationUserDetails authenticationUserDetails, @PathVariable int id){
        try {
            String group = authenticationUserDetails.getAuthorities().get(0).toString();
            int userId = authenticationUserDetails.getId();

            switch (group){
                case "ADMIN":
                    InstructorAdminOutput instructorAdminOutput = this.instructorService.getInstructorDetailsAsAdmin(id);
                    model.addAttribute("instructorAdminOutput", instructorAdminOutput);
                    return "admin/instructor_details_admin";
                case "STUDENT":
                    return "student/instructor_details";
                default:
                    //TODO do 403?
                    return null;
            }
        }
        catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 202);
            return "redirect:/instructor";
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
    public String deleteInstructor(RedirectAttributes redirectAttributes, @PathVariable int id) {
        try {
            this.instructorService.deleteInstructor(id);
            redirectAttributes.addFlashAttribute("code", 101);
        } catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/instructor";
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
