package iRide.service.Admin;

import iRide.model.Admin;
import iRide.model.Student;
import iRide.model.User;
import iRide.repository.AdminRepository;
import iRide.service.Admin.model.input.AdminCreateInput;
import iRide.service.User.UserService;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final UserService userService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(UserService userService, AdminRepository adminRepository) {
        this.userService = userService;
        this.adminRepository = adminRepository;
    }

    public void createAdmin(AdminCreateInput adminCreateInput, User user) throws DataExistsException {
        Admin admin = new Admin(adminCreateInput);
        admin.setUser(user);
        adminRepository.save(admin);
    }

    public List<Admin> getAll(){
        return this.adminRepository.findAll();
    }

    public int deleteAdmin(int id) {
        Admin admin = getAdmin(id);
        this.adminRepository.delete(admin);
        return id;
    }

    public Admin getAdmin(int adminId) {
        Optional<Admin> result = this.adminRepository.findById(adminId);
        if (!result.isPresent()) {
            throw new NotFoundException("Admin with id = " + adminId + " has not been found");
        }
        return result.get();
    }

}
