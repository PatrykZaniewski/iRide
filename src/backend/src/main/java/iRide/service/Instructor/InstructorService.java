package iRide.service.Instructor;

import iRide.model.Instructor;
import iRide.model.User;
import iRide.repository.InstructorRepository;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.service.User.UserService;
import iRide.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {

    private final UserService userService;
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(UserService userService, InstructorRepository instructorRepository) {
        this.userService = userService;
        this.instructorRepository = instructorRepository;
    }

    public int createInstructor(InstructorCreateInput instructorCreateInput) {
        Instructor instructor = new Instructor(instructorCreateInput);
        User user = userService.createLogin(instructorCreateInput.getLoginCreateInput(), "INSTRUCTOR");
        instructor.setLogin(user);
        return instructorRepository.save(instructor).getId();
    }

    public Instructor getInstructorById(int instructorId) throws NotFoundException {
        Optional<Instructor> instructor = instructorRepository.findById(instructorId);
        if (!instructor.isPresent()) {
            throw new NotFoundException("Instructor with id = " + instructorId + " has not been found");
        }
        return instructor.get();
    }


}
