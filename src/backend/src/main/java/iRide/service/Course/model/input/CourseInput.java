package iRide.service.Course.model.input;

public class CourseInput {
    private int studentId;
    private int instructorId;
    private int categoryId;
    private int hoursMinimum;
    private int hoursRemaining;

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

    public int getHoursMinimum() {
        return hoursMinimum;
    }

    public void setHoursMinimum(int hoursMinimum) {
        this.hoursMinimum = hoursMinimum;
    }

    public int getHoursRemaining() {
        return hoursRemaining;
    }

    public void setHoursRemaining(int hoursRemaining) {
        this.hoursRemaining = hoursRemaining;
    }
}
