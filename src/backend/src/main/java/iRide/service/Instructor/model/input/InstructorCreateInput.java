package iRide.service.Instructor.model.input;

import iRide.service.InstructorCategory.model.input.InstructorCategoryInput;
import iRide.service.User.model.input.UserCreateInput;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class InstructorCreateInput {

    @NotNull
    @Valid
    private String email;
    @NotNull
    private String password;
    @NotNull(message = "test")
    @Valid
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private LocalDateTime employmentDate;
    @NotNull
    private String phoneNumber;
    private List<InstructorCategoryInput> categories;

    public UserCreateInput getLoginCreateInput() {
        return new UserCreateInput(this.email, this.password);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<InstructorCategoryInput> getCategories() {
        return categories;
    }

    public void setCategories(List<InstructorCategoryInput> categories) {
        this.categories = categories;
    }
}
