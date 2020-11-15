package iRide.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "instructor_vehicle")
public class InstructorVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", referencedColumnName = "id")
    @NotNull
    private Instructor instructor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id")
    @NotNull
    private Vehicle vehicle;

    public InstructorVehicle() {

    }

    public InstructorVehicle(Instructor instructor, Vehicle vehicle) {
        this.instructor = instructor;
        this.vehicle = vehicle;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
