package iRide.service.Instructor;

import iRide.model.Category;
import iRide.model.Instructor;
import iRide.model.User;
import iRide.repository.InstructorRepository;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.service.Instructor.model.output.InstructorListOutput;
import iRide.service.InstructorCategory.InstructorCategoryService;
import iRide.service.User.UserService;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<InstructorListOutput> getInstructorListOutput(){
        List<Instructor> instructors = this.instructorRepository.findAll();
        List<InstructorListOutput> instructorListOutputs = new ArrayList<>();
        for (Instructor instructor: instructors){
            InstructorListOutput instructorListOutput = new InstructorListOutput();

            instructorListOutput.setFirstname(instructor.getFirstname());
            instructorListOutput.setLastname(instructor.getLastname());
            instructorListOutput.setId(instructor.getId());

            List<String> theory = new ArrayList<>();
            List<String> practice = new ArrayList<>();

            for(Category category: instructor.getCategories()){
                if (category.getCategoryType().equals("THEORY")){
                    theory.add(category.getCategoryName());
                }
                if (category.getCategoryType().equals("PRACTICE")){
                    practice.add(category.getCategoryName());
                }
            }
            instructorListOutput.setPractice(practice.stream().sorted(Comparator.comparing((String::toString))).collect(Collectors.toList()));
            instructorListOutput.setTheory(theory.stream().sorted(Comparator.comparing((String::toString))).collect(Collectors.toList()));

            instructorListOutputs.add(instructorListOutput);
        }
        return instructorListOutputs;
    }

    public Instructor getInstructor(int instructorId) throws NotFoundException {
        Optional<Instructor> result = instructorRepository.findById(instructorId);
        if (!result.isPresent()) {
            throw new NotFoundException("Instructor with id = " + instructorId + " has not been found");
        }
        return result.get();
    }

    public List<Instructor> getAll(){
        return this.instructorRepository.findAll();
    }

    public int deleteInstructor(int id) {
        instructorRepository.deleteById(id);
        return id;
    }


}
