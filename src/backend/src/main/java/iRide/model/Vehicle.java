package iRide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iRide.service.Vehicle.model.input.VehicleCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @NotNull
    private String mark;
    @NotNull
    private String model;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private Category category;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "instructor_vehicle",
            joinColumns = {@JoinColumn(name = "id_vehicle")},
            inverseJoinColumns = {@JoinColumn(name = "id_instructor")}
    )
    private Set<Instructor> instructors = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events;
    @NotNull
    private String plateNumber;
    @NotNull
    private String vin;

    public Vehicle() {

    }

    public Vehicle(VehicleCreateInput vehicleCreateInput) {
        this.mark = vehicleCreateInput.getMark();
        this.model = vehicleCreateInput.getModel();
        this.plateNumber = vehicleCreateInput.getPlateNumber();
        this.vin = vehicleCreateInput.getVin();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
