package iRide.service.Instructor;

import iRide.model.Instructor;
import iRide.model.Login;
import iRide.repository.InstructorRepository;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.service.Login.LoginService;
import iRide.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {

    private final LoginService loginService;
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(LoginService loginService, InstructorRepository instructorRepository) {
        this.loginService = loginService;
        this.instructorRepository = instructorRepository;
    }

    public int createInstructor(InstructorCreateInput instructorCreateInput) throws EmailExistsException {
        Login login = loginService.createLogin(instructorCreateInput.getLoginCreateInput(), "INSTRUCTOR");
        Instructor instructor = instructorRepository.save(new Instructor(instructorCreateInput, login));
        return instructor.getId();
    }
}
