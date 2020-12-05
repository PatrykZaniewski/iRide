package iRide.service.Student.model.output;

import java.util.Map;

public class StudentListOutput {
    private int id;
    private String firstname;
    private String lastname;
    private Map<Integer, String> activeCourses;
    private Map<Integer, String> historicCourses;

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

    public Map<Integer, String> getActiveCourses() {
        return activeCourses;
    }

    public void setActiveCourses(Map<Integer, String> activeCourses) {
        this.activeCourses = activeCourses;
    }

    public Map<Integer, String> getHistoricCourses() {
        return historicCourses;
    }

    public void setHistoricCourses(Map<Integer, String> historicCourses) {
        this.historicCourses = historicCourses;
    }
}
