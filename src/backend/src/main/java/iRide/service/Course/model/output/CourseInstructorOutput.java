package iRide.service.Course.model.output;

import java.util.Map;

public class CourseInstructorOutput {
    private int id;
    private int studentId;
    private String student;
    private int instructorId;
    private String instructor;
    private int hoursMinimum;
    private int hoursRemaining;
    private int hoursDone;
    private String status;
    private String category;
    private Map<Integer, String> events;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getHoursMinimum() {
        return hoursMinimum;
    }

    public void setHoursMinimum(int hoursMinimum) {
        this.hoursMinimum = hoursMinimum;
    }

    public int getHoursRemaining() {
        return hoursRemaining;
    }

    public void setHoursRemaining(int hoursRemaining) {
        this.hoursRemaining = hoursRemaining;
    }

    public int getHoursDone() {
        return hoursDone;
    }

    public void setHoursDone(int hoursDone) {
        this.hoursDone = hoursDone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<Integer, String> getEvents() {
        return events;
    }

    public void setEvents(Map<Integer, String> events) {
        this.events = events;
    }
}
