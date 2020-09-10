package iRide.service.Admin;

import iRide.model.Admin;
import iRide.model.Instructor;
import iRide.model.Login;
import iRide.repository.AdminRepository;
import iRide.repository.InstructorRepository;
import iRide.service.Admin.model.AdminCreateInput;
import iRide.service.Instructor.model.InstructorCreateInput;
import iRide.service.Login.LoginService;
import iRide.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final LoginService loginService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(LoginService loginService, AdminRepository adminRepository) {
        this.loginService = loginService;
        this.adminRepository = adminRepository;
    }

    public int createAdmin(AdminCreateInput adminCreateInput) throws EmailExistsException {
        Login login = loginService.createLogin(adminCreateInput.getLoginCreateInput(), "ADMIN");
        Admin admin = adminRepository.save(new Admin(adminCreateInput, login));
        return admin.getId();
    }
}
