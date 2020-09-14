package iRide.service.Instructor.model.input;

import iRide.model.Category;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.service.model.LoginCreateInput;

import java.time.LocalDateTime;
import java.util.List;

public class InstructorCreateInput {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDateTime employmentDate;
    private String phoneNumber;
    //TODO moze przejsc na inny model tak zeby modele z pakietu model były tylko do mapowania hibernate?
    private List<Category> categories;

    public LoginCreateInput getLoginCreateInput(){
        return new LoginCreateInput(this.email, this.password);
    }

    public Boolean checkDataCompleteness(){
        return email != null && password != null && firstname != null && lastname != null && phoneNumber != null && employmentDate != null && categories != null;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
