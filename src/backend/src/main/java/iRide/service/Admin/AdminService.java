package iRide.service.Admin;

import iRide.model.Admin;
import iRide.model.User;
import iRide.repository.AdminRepository;
import iRide.service.Admin.model.input.AdminCreateInput;
import iRide.service.User.UserService;
import iRide.utils.exception.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserService userService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(UserService userService, AdminRepository adminRepository) {
        this.userService = userService;
        this.adminRepository = adminRepository;
    }

    public int createAdmin(AdminCreateInput adminCreateInput) throws DataExistsException {
        Admin admin = new Admin(adminCreateInput);
        User user = userService.createLogin(adminCreateInput.getLoginCreateInput(), "ADMIN");
        admin.setLogin(user);
        return adminRepository.save(admin).getId();
    }
}
