package iRide.service.User;

import iRide.model.Admin;
import iRide.model.Instructor;
import iRide.model.Student;
import iRide.model.User;
import iRide.repository.UserRepository;
import iRide.service.Admin.AdminService;
import iRide.service.Instructor.InstructorService;
import iRide.service.Student.StudentService;
import iRide.service.User.model.input.UserCreateInput;
import iRide.service.User.model.output.UserListAdminOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AdminService adminService;
    private final InstructorService instructorService;
    private final StudentService studentService;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AdminService adminService, @Lazy InstructorService instructorService, @Lazy StudentService studentService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    public User createUser(UserCreateInput userCreateInput, String accountGroup) throws DataExistsException {
        if (userRepository.getUserByEmail(userCreateInput.getEmail()).isPresent()) {
            throw new DataExistsException("Email: " + userCreateInput.getEmail() + " is currently in usage.");
        }
        User user = new User(userCreateInput, accountGroup);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) throws NotFoundException {
        Optional<User> test = userRepository.getUserByEmail(email);
        if (userRepository.getUserByEmail(email).isPresent()) {
            return userRepository.getUserByEmail(email).get();
        }
        throw new NotFoundException("Typed email not found");
    }

    public User getUser(int userId) {
        Optional<User> result = this.userRepository.findById(userId);
        if (!result.isPresent()) {
            throw new NotFoundException("User with id = " + userId + " has not been found");
        }
        return result.get();
    }

    @Transactional
    public int deleteById(int id, String group) {
        User user = getUser(id);
        switch (group) {
            case "ADMIN":
                int adminToDelete = 0;
                for (Admin admin : this.adminService.getAll()) {
                    if (admin.getUser().getId() == id) {
                        adminToDelete = admin.getId();
                        break;
                    }
                }
                this.adminService.deleteAdmin(adminToDelete);
                break;
            case "INSTRUCTOR":
                int instructorToDelete = 0;
                for (Instructor instructor : this.instructorService.getAll()) {
                    if (instructor.getUser().getId() == id) {
                        instructorToDelete = instructor.getId();
                        break;
                    }
                }
                this.instructorService.deleteInstructor(instructorToDelete);
                break;
            case "STUDENT":
                int studentToDelete = 0;
                for (Student student : this.studentService.getAll()) {
                    if (student.getUser().getId() == id) {
                        studentToDelete = student.getId();
                        break;
                    }
                }
                this.studentService.deleteStudent(studentToDelete);
                break;
        }
        this.userRepository.delete(user);
        return id;
    }

    public List<UserListAdminOutput> getUserListAdminOutput() {
        List<User> users = this.userRepository.findAll();
        List<UserListAdminOutput> userListAdminOutputs = new ArrayList<>();
        for (User user : users) {
            UserListAdminOutput userListAdminOutput = new UserListAdminOutput();

            userListAdminOutput.setId(user.getId());
            userListAdminOutput.setEmail(user.getEmail());
            userListAdminOutput.setCreationDate(String.valueOf(user.getCreationDate()));
            userListAdminOutput.setStatus(mapParameters(user.getStatus()));
            userListAdminOutput.setAccountRole(mapParameters(user.getAccountRole()));

            userListAdminOutputs.add(userListAdminOutput);
        }
        return userListAdminOutputs;
    }

    private String mapParameters(String param) {
        switch (param) {
            case "THEORY":
                return "Teoria";
            case "PRACTICE":
                return "Praktyka";
            case "IN_PROGRESS":
                return "W trakcie";
            case "FINISHED":
                return "Ukończony";
            case "CANCELLED":
                return "Przerwany";
            case "WAITING":
                return "Oczekuje na rozpoczęcie";
            case "ACTIVE":
                return "Aktywny";
            case "PENDING_CONFIRMATION":
                return "Email niepotwierdzony";
            case "BLOCKED":
                return "Zablokowany";
            case "STUDENT":
                return "Kursant";
            case "INSTRUCTOR":
                return "Instruktor";
            case "ADMIN":
                return "Administrator";
            default:
                return "Status nieznany";
        }
    }


}
