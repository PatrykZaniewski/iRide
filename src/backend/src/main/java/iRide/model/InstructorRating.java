package iRide.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "instructor_rating")
public class InstructorRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;


}
