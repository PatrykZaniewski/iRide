package iRide.service.Student;

import iRide.model.Login;
import iRide.model.Student;
import iRide.repository.StudentRepository;
import iRide.service.Login.LoginService;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.utils.exceptions.DataExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final LoginService loginService;

    @Autowired
    public StudentService(StudentRepository studentRepository, LoginService loginService) {
        this.studentRepository = studentRepository;
        this.loginService = loginService;
    }

    public int createStudent(StudentCreateInput studentCreateInput) throws DataExistsException {
        Student student = studentRepository.save(new Student(studentCreateInput));
        Login login = loginService.createLogin(studentCreateInput.getLoginCreateInput(), "STUDENT");
        student.setLogin(login);
        return studentRepository.save(student).getId();
    }




}
