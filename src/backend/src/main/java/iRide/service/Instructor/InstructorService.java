package iRide.service.Instructor;

import iRide.model.*;
import iRide.repository.InstructorRepository;
import iRide.service.Instructor.model.input.InstructorCreateInput;
import iRide.service.Instructor.model.output.InstructorAdminOutput;
import iRide.service.Instructor.model.output.InstructorListAdminOutput;
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
    public void createInstructor(InstructorCreateInput instructorCreateInput, User user) {
        Instructor instructor = new Instructor(instructorCreateInput);
        instructor.setUser(user);
        instructorRepository.save(instructor);
    }

    public List<InstructorListAdminOutput> getInstructorListAdminOutput(){
        List<Instructor> instructors = this.instructorRepository.findAll();
        List<InstructorListAdminOutput> instructorListAdminOutputs = new ArrayList<>();
        for (Instructor instructor: instructors){
            InstructorListAdminOutput instructorListAdminOutput = new InstructorListAdminOutput();

            instructorListAdminOutput.setFirstname(instructor.getFirstname());
            instructorListAdminOutput.setLastname(instructor.getLastname());
            instructorListAdminOutput.setId(instructor.getId());
            instructorListAdminOutput.setUserId(instructor.getUser().getId());

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
            instructorListAdminOutput.setPractice(practice.stream().sorted(Comparator.comparing((String::toString))).collect(Collectors.toList()));
            instructorListAdminOutput.setTheory(theory.stream().sorted(Comparator.comparing((String::toString))).collect(Collectors.toList()));

            instructorListAdminOutputs.add(instructorListAdminOutput);
        }
        return instructorListAdminOutputs;
    }

    public InstructorAdminOutput getInstructorDetailsAsAdmin(int id){
        Instructor instructor = this.getInstructor(id);

        InstructorAdminOutput instructorAdminOutput = new InstructorAdminOutput();

        instructorAdminOutput.setId(instructor.getId());
        instructorAdminOutput.setFirstname(instructor.getFirstname());
        instructorAdminOutput.setLastname(instructor.getLastname());
        instructorAdminOutput.setEmail(instructor.getUser().getEmail());
        instructorAdminOutput.setEmploymentDate(instructor.getEmploymentDate());
        instructorAdminOutput.setDismissalDate(instructor.getDismissalDate());
        instructorAdminOutput.setPhoneNumber(instructor.getPhoneNumber());

        List<String> theory = new ArrayList<>();
        List<String> practice = new ArrayList<>();

        for (Category category: instructor.getCategories()){
            if(category.getCategoryType().equals("PRACTICE")){
                practice.add(category.getCategoryName());
            }
            if(category.getCategoryType().equals("THEORY")){
                theory.add(category.getCategoryName());
            }
        }
        instructorAdminOutput.setTheory(theory.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        instructorAdminOutput.setPractice(practice.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));

        Map<Integer, String> vehicles = new HashMap<>();
        for (Vehicle vehicle: instructor.getVehicles()){
            vehicles.put(vehicle.getId(), vehicle.getId() + "-" + vehicle.getMark() + " " + vehicle.getModel());
        }
        instructorAdminOutput.setVehicles(vehicles);

        Map<Integer, String> activeCourses = new HashMap<>();
        List<String> activeStatuses = new ArrayList<>(Arrays.asList("WAITING", "IN_PROGRESS"));
        for (Course course: instructor.getCourses()){
            if(activeStatuses.contains(course.getStatus())){
                activeCourses.put(course.getId(), course.getId() + "." + mapParameters(course.getCategory().getCategoryType()) + "-" + course.getCategory().getCategoryName());
            }
        }
        instructorAdminOutput.setActiveCourses(activeCourses);

        return instructorAdminOutput;
    }

    public Instructor getInstructor(int instructorId) throws NotFoundException {
        Optional<Instructor> result = instructorRepository.findById(instructorId);
        if (!result.isPresent()) {
            throw new NotFoundException("Instructor with id = " + instructorId + " has not been found");
        }
        return result.get();
    }

    public Instructor getInstructorByUserId(int userId) throws NotFoundException {
        Optional<Instructor> result = this.instructorRepository.getInstructorByUserId(userId);
        if (!result.isPresent()) {
            throw new NotFoundException("Instructor with user_id = " + userId + " has not been found");
        }
        return result.get();
    }

    public List<Instructor> getAllInstructors(){
        return this.instructorRepository.findAll();
    }

    @Transactional
    public int deleteInstructor(int id) {
        Instructor instructor = getInstructor(id);
        instructorRepository.delete(instructor);
        return id;
    }

    private String mapParameters(String param){
        switch (param){
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
            default:
                return "Status nieznany";
        }
    }


}
