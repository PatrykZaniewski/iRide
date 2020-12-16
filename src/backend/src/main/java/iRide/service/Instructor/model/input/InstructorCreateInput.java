package iRide.service.Instructor.model.input;

import iRide.service.InstructorCategory.model.input.InstructorCategoryInput;
import iRide.service.User.model.input.UserCreateInput;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class InstructorCreateInput {

    private String firstname;
    private String lastname;
    private String phoneNumber;

    public InstructorCreateInput(UserCreateInput userCreateInput){
        this.firstname = userCreateInput.getFirstname();
        this.lastname = userCreateInput.getLastname();
        this.phoneNumber = userCreateInput.getPhoneNumber();
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
