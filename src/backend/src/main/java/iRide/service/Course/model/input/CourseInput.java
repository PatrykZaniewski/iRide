package iRide.service.Course.model.input;

public class CourseInput {
    private int studentId;
    private int instructorId;
    private int categoryId;
    private int hours_minimum;
    private int hours_remaining;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getHours_minimum() {
        return hours_minimum;
    }

    public void setHours_minimum(int hours_minimum) {
        this.hours_minimum = hours_minimum;
    }

    public int getHours_remaining() {
        return hours_remaining;
    }

    public void setHours_remaining(int hours_remaining) {
        this.hours_remaining = hours_remaining;
    }
}
