package iRide.service.Student;

import iRide.model.User;
import iRide.model.Student;
import iRide.repository.StudentRepository;
import iRide.service.User.UserService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.utils.exception.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    @Autowired
    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public int createStudent(StudentCreateInput studentCreateInput) throws DataExistsException {
        Student student = studentRepository.save(new Student(studentCreateInput));
        User user = userService.createLogin(studentCreateInput.getLoginCreateInput(), "STUDENT");
        student.setLogin(user);
        return studentRepository.save(student).getId();
    }




}
