package iRide.controller;

import iRide.service.Admin.AdminService;
import iRide.service.Admin.model.AdminCreateInput;
import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.InstructorCreateInput;
import iRide.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createAdmin(@RequestBody AdminCreateInput adminCreateInput){
        if (!adminCreateInput.checkDataCompleteness()){
            return new ResponseEntity<>("Incomplete request data.", HttpStatus.BAD_REQUEST);
        }
        try {
            int adminId = adminService.createAdmin(adminCreateInput);
            return ResponseEntity.ok("Instructor account has been created. Student id = " + adminId);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
