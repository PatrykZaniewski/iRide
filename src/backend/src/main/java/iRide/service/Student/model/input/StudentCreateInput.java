package iRide.service.Student.model.input;

import iRide.service.User.model.input.UserCreateInput;

public class StudentCreateInput {
    private String firstname;
    private String lastname;
    private String phoneNumber;

    public StudentCreateInput(UserCreateInput userCreateInput){
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

