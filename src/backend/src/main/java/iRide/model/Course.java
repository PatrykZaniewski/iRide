package iRide.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    @NotNull
    private Student student;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", referencedColumnName = "id")
    @NotNull
    private Instructor instructor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    @NotNull
    private Category category;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @NotNull
    private List<Event> event;
    @Column(name = "hours_remaining")
    @NotNull
    private int hoursRemaining;
    @Column(name = "hours_done")
    @NotNull
    private int hoursDone;
    @NotNull
    private String status;


}
