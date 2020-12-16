package iRide.service.Vehicle.model.output;

import java.util.Map;

public class VehicleInstructorOutput {

    private int id;
    private String mark;
    private String model;
    private String category;
    private Map<Integer, String> instructors;
    private String plateNumber;
    private String vin;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<Integer, String> getInstructors() {
        return instructors;
    }

    public void setInstructors(Map<Integer, String> instructors) {
        this.instructors = instructors;
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
