package iRide.model;

import iRide.service.model.LoginCreateInput;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @NotNull
    private String status;
    @NotNull
    @Column(name = "account_role")
    private String accountRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(String accountRole) {
        this.accountRole = accountRole;
    }

    public User() {

    }

    public User(LoginCreateInput loginCreateInput, String accountRole) {
        this.email = loginCreateInput.getEmail();
        this.password = loginCreateInput.getPassword();
        this.creationDate = LocalDateTime.now();
        this.status = "ACTIVE";
        this.accountRole = accountRole;
    }
}
