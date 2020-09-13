package iRide.service.Admin;

import iRide.model.Admin;
import iRide.model.Login;
import iRide.repository.AdminRepository;
import iRide.service.Admin.model.input.AdminCreateInput;
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
