package iRide.service.Instructor.model.output;

import java.util.List;

public class InstructorListOutput {
    private int id;
    private String firstname;
    private String lastname;
    private List<String> theory;
    private List<String> practice;

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
}
