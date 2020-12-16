package iRide.service.Vehicle.model.output;

import java.util.Map;

public class VehicleCreateOutput {
    private Map<Integer, String> categories;

    public Map<Integer, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<Integer, String> categories) {
        this.categories = categories;
    }
}
