package iDrive.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "intructor_vehicle")
public class InstructorVehicle {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", referencedColumnName = "id")
    @NotNull
    private Instructor instructor;
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id")
    @NotNull
    private Vehicle vehicle;

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
