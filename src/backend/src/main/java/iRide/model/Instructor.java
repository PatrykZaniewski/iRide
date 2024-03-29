package iRide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iRide.service.Instructor.model.input.InstructorCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "instructor")
public class Instructor {
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
    @Column(name = "employment_date")
    private LocalDateTime employmentDate;
    @Column(name = "dismissal_date")
    private LocalDateTime dismissalDate;
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "instructor_category",
            joinColumns = {@JoinColumn(name = "id_instructor")},
            inverseJoinColumns = {@JoinColumn(name = "id_category")}
    )
    private Set<Category> categories;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "instructor_vehicle",
            joinColumns = {@JoinColumn(name = "id_instructor")},
            inverseJoinColumns = {@JoinColumn(name = "id_vehicle")}
    )
    private Set<Vehicle> vehicles = new HashSet<>();
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events;
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InstructorRating> instructorRatings;


    public Instructor() {

    }

    public Instructor(InstructorCreateInput instructorCreateInput) {
        this.firstname = instructorCreateInput.getFirstname();
        this.lastname = instructorCreateInput.getLastname();
        this.phoneNumber = instructorCreateInput.getPhoneNumber();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
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

    public List<InstructorRating> getInstructorRatings() {
        return instructorRatings;
    }

    public void setInstructorRatings(List<InstructorRating> instructorRatings) {
        this.instructorRatings = instructorRatings;
    }
}
