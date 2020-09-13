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
    @JoinColumn(name = "id_login", referencedColumnName = "id")
    @NotNull
    private Login login;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String phoneNumber;

    public Admin() {

    }

    public Admin(AdminCreateInput adminCreateInput, Login login) {
        this.firstname = adminCreateInput.getFirstname();
        this.lastname = adminCreateInput.getLastname();
        this.phoneNumber = adminCreateInput.getPhoneNumber();
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
