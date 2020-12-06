package iRide.service.Instructor.model.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InstructorAdminOutput {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDateTime employmentDate;
    private LocalDateTime dismissalDate;
    private String phoneNumber;
    private List<String> theory;
    private List<String> practice;
    private Map<Integer, String> vehicles;
    private Map<Integer, String> activeCourses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDateTime employmentDate) {
        this.employmentDate = employmentDate;
    }

    public LocalDateTime getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(LocalDateTime dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getTheory() {
        return theory;
    }

    public void setTheory(List<String> theory) {
        this.theory = theory;
    }

    public List<String> getPractice() {
        return practice;
    }

    public void setPractice(List<String> practice) {
        this.practice = practice;
    }

    public Map<Integer, String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Map<Integer, String> vehicles) {
        this.vehicles = vehicles;
    }

    public Map<Integer, String> getActiveCourses() {
        return activeCourses;
    }

    public void setActiveCourses(Map<Integer, String> activeCourses) {
        this.activeCourses = activeCourses;
    }
}
