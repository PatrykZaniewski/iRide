package iRide.service.Course;

import iRide.model.Category;
import iRide.model.Course;
import iRide.model.Instructor;
import iRide.model.Student;
import iRide.repository.CourseRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Course.model.input.CourseInput;
import iRide.service.Course.model.output.CourseListOutput;
import iRide.service.Instructor.InstructorService;
import iRide.service.Student.StudentService;
import iRide.service.Vehicle.model.output.VehicleListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final CategoryService categoryService;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorService instructorService, StudentService studentService, CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.categoryService = categoryService;
    }

    public int deleteCategory(int id) {
        this.courseRepository.deleteById(id);
        return id;
    }


    public List<Course> getCourseByParameters(Integer instructorId, Integer studentId, Integer categoryId, List<String> status) {
        String instructorIdString = instructorId == null ? "%" : String.valueOf(instructorId);
        String studentIdString = studentId == null ? "%" : String.valueOf(studentId);
        String categoryIdString = categoryId == null ? "%" : String.valueOf(categoryId);
        Optional<List<Course>> result = this.courseRepository.getCoursesByParameters(instructorIdString, studentIdString, categoryIdString, status);
        return result.orElse(Collections.emptyList());
    }

    public List<CourseListOutput> getCourseListOutput() {
        List<Course> courses = this.courseRepository.findAll();
        List<CourseListOutput> courseListOutputs = new ArrayList<>();

        for (Course course: courses){
            CourseListOutput courseListOutput = new CourseListOutput();

            courseListOutput.setId(course.getId());

            courseListOutput.setCategoryName(course.getCategory().getCategoryName());
            courseListOutput.setCategoryType(mapParameters(course.getCategory().getCategoryType()));
            courseListOutput.setStatus(mapParameters(course.getStatus()));
            courseListOutput.setInstructor(course.getInstructor().getFirstname() + " " + course.getInstructor().getLastname());
            courseListOutput.setStudent(course.getStudent().getFirstname() + " " + course.getStudent().getLastname());

            courseListOutputs.add(courseListOutput);
        }
        return courseListOutputs;
    }


    public int createCourse(CourseInput courseInput) {
        //TODO walidacja czy podane wartosci istnieja
        Instructor instructor = this.instructorService.getInstructor(courseInput.getInstructorId());
        Student student = this.studentService.getStudent(courseInput.getInstructorId());
        Category category = this.categoryService.getCategory(courseInput.getCategoryId());
        Course course = new Course(courseInput, instructor, student, category);
        return this.courseRepository.save(course).getId();
        //TODO sprawdzic czy jest kurs na dana kategorie w stanie innym niz FINISHED i czy instruktor moze prowadzic dana kategorie!
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
