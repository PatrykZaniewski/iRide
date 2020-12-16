package iRide.controller;

import iRide.service.Admin.AdminService;
import iRide.service.Admin.model.input.AdminCreateInput;
import iRide.utils.exception.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createAdmin(@RequestBody AdminCreateInput adminCreateInput) {
        try {
//            int adminId = adminService.createAdmin(adminCreateInput);
            return ResponseEntity.ok("Instructor account has been created. Student id = ");
        } catch (DataExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
