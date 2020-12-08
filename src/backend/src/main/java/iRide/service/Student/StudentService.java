package iRide.service.Student;

import iRide.model.Course;
import iRide.model.Student;
import iRide.model.User;
import iRide.repository.StudentRepository;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.service.Student.model.output.StudentAdminOutput;
import iRide.service.Student.model.output.StudentListOutput;
import iRide.service.User.UserService;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    @Autowired
    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public List<StudentListOutput> getStudentListOutput() {
        List<Student> students = this.studentRepository.findAll();
        List<StudentListOutput> studentListOutputs = new ArrayList<>();

        for (Student student : students) {
            StudentListOutput studentListOutput = new StudentListOutput();

            studentListOutput.setId(student.getId());
            studentListOutput.setFirstname(student.getFirstname());
            studentListOutput.setLastname(student.getLastname());

            Map<Integer, String> activeCourses = new HashMap<>();
            Map<Integer, String> historicCourses = new HashMap<>();

            List<String> activeCourseStatuses = new ArrayList<>(Arrays.asList("WAITING", "IN_PROGRESS"));
            List<String> historicCourseStatuses = new ArrayList<>(Arrays.asList("CANCELLED", "FINISHED"));
            for (Course course : student.getCourses()) {
                if (activeCourseStatuses.contains(course.getStatus())) {
                    activeCourses.put(course.getId(), course.getCategory().getCategoryName() + " - " + course.getCategory().getCategoryType());
                }
                if (historicCourseStatuses.contains(course.getStatus())) {
                    historicCourses.put(course.getId(), course.getCategory().getCategoryName() + " - " + course.getCategory().getCategoryType());
                }
            }
            studentListOutput.setActiveCourses(activeCourses);
            studentListOutput.setHistoricCourses(historicCourses);

            studentListOutputs.add(studentListOutput);
        }
        return studentListOutputs;
    }

    public StudentAdminOutput getStudentDetailsAsAdmin(int id) {
        Student student = this.getStudent(id);

        StudentAdminOutput studentAdminOutput = new StudentAdminOutput();

        studentAdminOutput.setId(student.getId());
        studentAdminOutput.setFirstname(student.getFirstname());
        studentAdminOutput.setLastname(student.getLastname());
        studentAdminOutput.setEmail(student.getLogin().getEmail());
        studentAdminOutput.setPhoneNumber(student.getPhoneNumber());

        Map<Integer, String> activeCourses = new HashMap<>();
        Map<Integer, String> historicCourses = new HashMap<>();

        for (Course course : student.getCourses()) {
            if(Arrays.asList("WAITING", "IN_PROGRESS").contains(course.getStatus())){
                activeCourses.put(course.getId(), course.getCategory().getCategoryName() + "-" + mapParameters(course.getCategory().getCategoryType()));
            }
            if(Arrays.asList("CANCELLED", "FINISHED").contains(course.getStatus())){
                historicCourses.put(course.getId(), course.getCategory().getCategoryName() + "-" + mapParameters(course.getCategory().getCategoryType()));
            }
        }

        studentAdminOutput.setActiveCourses(activeCourses);
        studentAdminOutput.setHistoricCourses(historicCourses);

        return studentAdminOutput;
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

    public int createStudent(StudentCreateInput studentCreateInput) throws DataExistsException {
        Student student = new Student(studentCreateInput);
        User user = userService.createUser(studentCreateInput.getLoginCreateInput(), "STUDENT");
        student.setLogin(user);
        return studentRepository.save(student).getId();
    }

    public Student getStudent(int studentId) {
        Optional<Student> result = this.studentRepository.findById(studentId);
        if (!result.isPresent()) {
            throw new NotFoundException("Studnt with id = " + studentId + " has not been found");
        }
        return result.get();
    }


}
