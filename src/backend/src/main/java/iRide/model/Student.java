package iRide.model;

import iRide.service.Student.model.input.StudentCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @NotNull
    private User user;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

    public Student() {

    }

    public Student(StudentCreateInput studentCreateInput) {
        this.firstname = studentCreateInput.getFirstname();
        this.lastname = studentCreateInput.getLastname();
        this.phoneNumber = studentCreateInput.getPhoneNumber();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getLogin() {
        return user;
    }

    public void setLogin(User user) {
        this.user = user;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
