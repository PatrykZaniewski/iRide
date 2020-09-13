package iRide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iRide.service.Instructor.model.input.InstructorCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_login", referencedColumnName = "id")
    @NotNull
    private Login login;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "instructor_category",
            joinColumns = {@JoinColumn(name = "id_instructor")},
            inverseJoinColumns = {@JoinColumn(name = "id_category")}
    )
    private Set<Category> categorySet;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "instructor_vehicles",
            joinColumns = {@JoinColumn(name = "id_instructor")},
            inverseJoinColumns = {@JoinColumn(name = "id_vehicle")}
    )
    private Set<Vehicle> vehicles = new HashSet<>();


    public Instructor(){

    }

    public Instructor(InstructorCreateInput instructorCreateInput, Login login){
        this.firstname = instructorCreateInput.getFirstname();
        this.lastname = instructorCreateInput.getLastname();
        this.phoneNumber = instructorCreateInput.getPhoneNumber();
        this.employmentDate = instructorCreateInput.getEmploymentDate();
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
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

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
