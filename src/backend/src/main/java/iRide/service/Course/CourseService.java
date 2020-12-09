package iRide.service.Course;

import iRide.model.*;
import iRide.repository.CourseRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Course.model.input.CourseInput;
import iRide.service.Course.model.output.CourseAdminOutput;
import iRide.service.Course.model.output.CourseListAdminOutput;
import iRide.service.Course.model.output.CourseListStudentOutput;
import iRide.service.Instructor.InstructorService;
import iRide.service.Student.StudentService;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final CategoryService categoryService;

    private final List<String> allCourseStatuses = new ArrayList<>(Arrays.asList("WAITING", "FINISHED", "IN_PROGRESS", "CANCELLED"));

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorService instructorService, StudentService studentService, CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.categoryService = categoryService;
    }

    @Transactional
    public int deleteCourse(int id) {
        Course course = getCourse(id);
        this.courseRepository.delete(course);
        return id;
    }


    public List<Course> getCourseByParameters(Integer instructorId, Integer studentId, Integer categoryId, List<String> status) {
        String instructorIdString = instructorId == null ? "%" : String.valueOf(instructorId);
        String studentIdString = studentId == null ? "%" : String.valueOf(studentId);
        String categoryIdString = categoryId == null ? "%" : String.valueOf(categoryId);
        Optional<List<Course>> result = this.courseRepository.getCoursesByParameters(instructorIdString, studentIdString, categoryIdString, status);
        return result.orElse(Collections.emptyList());
    }

    public List<CourseListAdminOutput> getCourseListAdminOutput() {
        List<Course> courses = this.courseRepository.findAll();
        List<CourseListAdminOutput> courseListAdminOutputs = new ArrayList<>();

        for (Course course : courses) {
            CourseListAdminOutput courseListAdminOutput = new CourseListAdminOutput();

            courseListAdminOutput.setId(course.getId());

            courseListAdminOutput.setCategoryName(course.getCategory().getCategoryName());
            courseListAdminOutput.setCategoryType(mapParameters(course.getCategory().getCategoryType()));
            courseListAdminOutput.setStatus(mapParameters(course.getStatus()));
            courseListAdminOutput.setInstructor(course.getInstructor().getFirstname() + " " + course.getInstructor().getLastname());
            courseListAdminOutput.setStudent(course.getStudent().getFirstname() + " " + course.getStudent().getLastname());

            courseListAdminOutputs.add(courseListAdminOutput);
        }
        return courseListAdminOutputs;
    }

    public List<Course> getCoursesByParameters(String instructorId, String studentId, String categoryId, List<String> statuses){
        Optional<List<Course>> courses = this.courseRepository.getCoursesByParameters(instructorId, studentId, categoryId, statuses);
        return courses.orElseGet(ArrayList::new);
    }

    public List<CourseListStudentOutput> getCourseListStudentOutput(int studentId){
        List<Course> courses = getCoursesByParameters("%", String.valueOf(studentId), "%", allCourseStatuses);
        List<CourseListStudentOutput> courseListStudentOutputs = new ArrayList<>();

        for(Course course: courses){
            CourseListStudentOutput courseListStudentOutput = new CourseListStudentOutput();

            courseListStudentOutput.setId(course.getId());
            courseListStudentOutput.setCategoryName(course.getCategory().getCategoryName());
            courseListStudentOutput.setCategoryType(mapParameters(course.getCategory().getCategoryType()));
            courseListStudentOutput.setStatus(course.getStatus());
            courseListStudentOutput.setInstructorId(course.getInstructor().getId());
            courseListStudentOutput.setInstructor(course.getInstructor().getFirstname() + " " + course.getInstructor().getLastname());

            courseListStudentOutputs.add(courseListStudentOutput);
        }

        return courseListStudentOutputs;
    }

    public CourseAdminOutput getCourseDetailsAsAdmin(int id) {
        Course course = this.getCourse(id);
        CourseAdminOutput courseAdminOutput = new CourseAdminOutput();

        courseAdminOutput.setId(course.getId());
        courseAdminOutput.setCategory(course.getCategory().getCategoryName() + "-" + mapParameters(course.getCategory().getCategoryType()));
        courseAdminOutput.setHoursDone(course.getHoursDone());
        courseAdminOutput.setHoursMinimum(course.getHoursMinimum());
        courseAdminOutput.setHoursRemaining(course.getHoursRemaining());
        courseAdminOutput.setInstructorId(course.getInstructor().getId());
        courseAdminOutput.setInstructor(course.getInstructor().getFirstname() + " " + course.getInstructor().getLastname());
        courseAdminOutput.setStudentId(course.getStudent().getId());
        courseAdminOutput.setStudent(course.getStudent().getFirstname() + " " + course.getStudent().getLastname());
        courseAdminOutput.setStatus(mapParameters(course.getStatus()));

        Map<Integer, String> events = new HashMap<>();
        for (Event event : course.getEvent()){
            events.put(event.getId(), String.valueOf(event.getStartDate()));
        }

        courseAdminOutput.setEvents(events);
        return courseAdminOutput;
    }

    public Course getCourse(int courseId) {
        Optional<Course> result = this.courseRepository.findById(courseId);
        if (!result.isPresent()) {
            throw new NotFoundException("Course with id = " + courseId + " has not been found");
        }
        return result.get();
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
            default:
                return "Status nieznany";
        }
    }


}
