package iRide.model;

import iRide.service.Course.model.input.CourseInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    @NotNull
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", referencedColumnName = "id")
    @NotNull
    private Instructor instructor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    @NotNull
    private Category category;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> event;
    @Column(name = "hours_minimum")
    @NotNull
    private int hoursMinimum;
    @Column(name = "hours_remaining")
    @NotNull
    private int hoursRemaining;
    @Column(name = "hours_done")
    @NotNull
    private int hoursDone;
    @NotNull
    private String status;

    public Course(){

    }

    public Course(CourseInput courseInput, Instructor instructor, Student student, Category category){
        this.instructor = instructor;
        this.student = student;
        this.category = category;
        //TODO zrobic na podstawie kategorii
        this.hoursRemaining = courseInput.getHoursRemaining();
        this.hoursDone = 0;
        this.hoursMinimum = courseInput.getHoursMinimum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public int getHoursRemaining() {
        return hoursRemaining;
    }

    public void setHoursRemaining(int hoursRemaining) {
        this.hoursRemaining = hoursRemaining;
    }

    public int getHoursDone() {
        return hoursDone;
    }

    public void setHoursDone(int hoursDone) {
        this.hoursDone = hoursDone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
