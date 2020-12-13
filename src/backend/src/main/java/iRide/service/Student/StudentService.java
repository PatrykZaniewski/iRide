package iRide.service.Student;

import iRide.model.Course;
import iRide.model.Student;
import iRide.model.User;
import iRide.repository.StudentRepository;
import iRide.service.Student.model.input.StudentCreateInput;
import iRide.service.Student.model.output.StudentAdminOutput;
import iRide.service.Student.model.output.StudentListAdminOutput;
import iRide.service.User.UserService;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<StudentListAdminOutput> getStudentListAdminOutput() {
        List<Student> students = this.studentRepository.findAll();
        List<StudentListAdminOutput> studentListAdminOutputs = new ArrayList<>();

        for (Student student : students) {
            StudentListAdminOutput studentListAdminOutput = new StudentListAdminOutput();

            studentListAdminOutput.setId(student.getId());
            studentListAdminOutput.setUserId(student.getUser().getId());
            studentListAdminOutput.setFirstname(student.getFirstname());
            studentListAdminOutput.setLastname(student.getLastname());

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
            studentListAdminOutput.setActiveCourses(activeCourses);
            studentListAdminOutput.setHistoricCourses(historicCourses);

            studentListAdminOutputs.add(studentListAdminOutput);
        }
        return studentListAdminOutputs;
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

    @Transactional
    public int deleteStudent(int id) {
        Student student = getStudent(id);
        studentRepository.delete(student);
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

    public List<Student> getAll(){
        return this.studentRepository.findAll();
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
            throw new NotFoundException("Student with id = " + studentId + " has not been found");
        }
        return result.get();
    }


}
