package iRide.service.Vehicle.model.output;

import iRide.model.Vehicle;

public class VehicleCreateOutput {

    private int id;
    private String mark;
    private String model;
    private String category;
    private String plateNumber;
    private String vin;

    public VehicleCreateOutput(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.mark = vehicle.getMark();
        this.model = vehicle.getModel();
        this.category = vehicle.getCategory() == null ? null : vehicle.getCategory().getCategoryName();
        this.plateNumber = vehicle.getPlateNumber();
        this.vin = vehicle.getVin();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
