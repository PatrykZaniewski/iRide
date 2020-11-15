package iRide.model;

import iRide.service.Admin.model.input.AdminCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "admin")
public class Admin {
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
    @NotNull
    private String phoneNumber;

    public Admin() {

    }

    public Admin(AdminCreateInput adminCreateInput) {
        this.firstname = adminCreateInput.getFirstname();
        this.lastname = adminCreateInput.getLastname();
        this.phoneNumber = adminCreateInput.getPhoneNumber();
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
}
