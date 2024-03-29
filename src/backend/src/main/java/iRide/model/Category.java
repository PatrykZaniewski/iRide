package iRide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iRide.service.Category.model.input.CategoryCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @NotNull
    @Column(name = "category_name")
    private String categoryName;
    @NotNull
    @Column(name = "category_type")
    //TODO moze enumy? moze nie
    private String categoryType;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "instructor_category",
            joinColumns = {@JoinColumn(name = "id_category")},
            inverseJoinColumns = {@JoinColumn(name = "id_instructor")}
    )
    private Set<Instructor> instructors;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;


    public Category() {

    }

    public Category(CategoryCreateInput categoryCreateInput) {
        this.categoryName = categoryCreateInput.getCategoryName();
        this.categoryType = categoryCreateInput.getCategoryType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Set<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
