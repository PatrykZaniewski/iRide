package iRide.service.Course.model.output;

import java.util.Map;

public class CourseCreateOutput {

    Map<Integer, String> instructors;
    Map<Integer, String> students;
    Map<Integer, String> categories;

    public Map<Integer, String> getInstructors() {
        return instructors;
    }

    public void setInstructors(Map<Integer, String> instructors) {
        this.instructors = instructors;
    }

    public Map<Integer, String> getStudents() {
        return students;
    }

    public void setStudents(Map<Integer, String> students) {
        this.students = students;
    }

    public Map<Integer, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<Integer, String> categories) {
        this.categories = categories;
    }
}
