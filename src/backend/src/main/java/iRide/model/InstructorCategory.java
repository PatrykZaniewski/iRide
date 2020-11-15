package iRide.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "instructor_category")
public class InstructorCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", referencedColumnName = "id")
    @NotNull
    private Instructor instructor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    @NotNull
    private Category category;

    public InstructorCategory() {

    }

    public InstructorCategory(Instructor instructor, Category category) {
        this.instructor = instructor;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
