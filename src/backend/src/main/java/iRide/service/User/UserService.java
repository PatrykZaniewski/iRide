package iRide.service.User;

import iRide.model.Admin;
import iRide.model.Instructor;
import iRide.model.Student;
import iRide.model.User;
import iRide.repository.UserRepository;
import iRide.service.Admin.AdminService;
import iRide.service.Admin.model.input.AdminCreateInput;
import iRide.service.Instructor.InstructorService;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.service.Student.StudentService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.service.User.model.input.UserCreateInput;
import iRide.service.User.model.output.UserCreateOutput;
import iRide.service.User.model.output.UserListAdminOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AdminService adminService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AdminService adminService, @Lazy InstructorService instructorService, @Lazy StudentService studentService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User createUser(UserCreateInput userCreateInput) throws DataExistsException {
        if (userRepository.getUserByEmail(userCreateInput.getEmail()).isPresent()) {
            throw new DataExistsException("Email: " + userCreateInput.getEmail() + " is currently in usage.");
        }

        User user = userRepository.save(new User(userCreateInput, bCryptPasswordEncoder.encode(userCreateInput.getPassword())));

        switch (userCreateInput.getGroup()) {

//            TODO zrobic maile
            case "INSTRUCTOR":
                InstructorCreateInput instructorCreateInput = new InstructorCreateInput(userCreateInput);
                this.instructorService.createInstructor(instructorCreateInput, user);
                break;
            case "ADMIN":
                AdminCreateInput adminCreateInput = new AdminCreateInput(userCreateInput);
                this.adminService.createAdmin(adminCreateInput, user);
                break;
            case "STUDENT":
                StudentCreateInput studentCreateInput = new StudentCreateInput(userCreateInput);
                this.studentService.createStudent(studentCreateInput, user);
                break;
        }
        return user;
    }

    public User getUserByEmail(String email) throws NotFoundException {
        Optional<User> result = this.userRepository.getUserByEmail(email);
        if (!result.isPresent()) {
            throw new NotFoundException("Typed email not found");
        }
        return result.get();
    }

    public User getUser(int userId) {
        Optional<User> result = this.userRepository.findById(userId);
        if (!result.isPresent()) {
            throw new NotFoundException("User with id = " + userId + " has not been found");
        }
        return result.get();
    }

    public UserCreateOutput getCreateUser() {
        UserCreateOutput userCreateOutput = new UserCreateOutput();
        Map<String, String> groupsMap = new HashMap<>();
        String[] groups = {"ADMIN", "INSTRUCTOR", "STUDENT"};

        for (String group : groups) {
            groupsMap.put(group, mapParameters(group));
        }
        userCreateOutput.setGroups(groupsMap);

        return userCreateOutput;
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
                for (Instructor instructor : this.instructorService.getAllInstructors()) {
                    if (instructor.getUser().getId() == id) {
                        instructorToDelete = instructor.getId();
                        break;
                    }
                }
                this.instructorService.deleteInstructor(instructorToDelete);
                break;
            case "STUDENT":
                int studentToDelete = 0;
                for (Student student : this.studentService.getAllStudents()) {
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
