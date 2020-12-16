package iRide.service.User.model.output;

import java.util.Map;

public class UserCreateOutput {

    private Map<String, String> groups;
    private String email;

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
