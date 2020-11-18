package iRide.service.Instructor;

import iRide.model.Instructor;
import iRide.model.User;
import iRide.repository.InstructorRepository;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.service.InstructorCategory.InstructorCategoryService;
import iRide.service.User.UserService;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InstructorService {

    private final UserService userService;
    private final InstructorRepository instructorRepository;
    private final InstructorCategoryService instructorCategoryService;

    @Autowired
    public InstructorService(UserService userService, InstructorRepository instructorRepository, InstructorCategoryService instructorCategoryService) {
        this.userService = userService;
        this.instructorRepository = instructorRepository;
        this.instructorCategoryService = instructorCategoryService;
    }

    @Transactional
    public int createInstructor(InstructorCreateInput instructorCreateInput) {
        Instructor instructor = new Instructor(instructorCreateInput);
        User user = userService.createUser(instructorCreateInput.getLoginCreateInput(), "INSTRUCTOR");
        instructor.setUser(user);
        int id = instructorRepository.save(instructor).getId();
        this.instructorCategoryService.assignCategoriesToInstructor(instructorCreateInput.getCategories(), id);
        return id;
    }

    public Instructor getInstructor(int instructorId) throws NotFoundException {
        Optional<Instructor> result = instructorRepository.findById(instructorId);
        if (!result.isPresent()) {
            throw new NotFoundException("Instructor with id = " + instructorId + " has not been found");
        }
        return result.get();
    }

    public int deleteInstructor(int id) {
        instructorRepository.deleteById(id);
        return id;
    }


}
