package iRide.service.Student;

import iRide.model.Student;
import iRide.model.User;
import iRide.repository.StudentRepository;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.service.User.UserService;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Student student = new Student(studentCreateInput);
        User user = userService.createUser(studentCreateInput.getLoginCreateInput(), "STUDENT");
        student.setLogin(user);
        return studentRepository.save(student).getId();
    }

    public Student getStudent(int studentId){
        Optional<Student> result = this.studentRepository.findById(studentId);
        if (!result.isPresent()) {
            throw new NotFoundException("Studnt with id = " + studentId + " has not been found");
        }
        return result.get();
    }


}
