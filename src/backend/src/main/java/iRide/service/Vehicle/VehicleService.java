package iRide.service.Vehicle;

import iRide.model.Category;
import iRide.model.Vehicle;
import iRide.repository.VehicleRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.service.Vehicle.model.input.VehicleUpdateInput;
import iRide.service.Vehicle.model.output.VehicleOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CategoryService categoryService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, CategoryService categoryService) {
        this.vehicleRepository = vehicleRepository;
        this.categoryService = categoryService;
    }

    public int deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
        return id;
    }

    public int updateVehicle(VehicleUpdateInput vehicleUpdateInput, int vehicleId) {
        Vehicle vehicle = getVehicle(vehicleId);
        if (vehicleUpdateInput.getMark() != null) {
            vehicle.setMark(vehicleUpdateInput.getMark());
        }
        if (vehicleUpdateInput.getModel() != null) {
            vehicle.setModel(vehicleUpdateInput.getModel());
        }
        if (vehicleUpdateInput.getPlateNumber() != null) {
            vehicle.setPlateNumber(vehicleUpdateInput.getPlateNumber());
        }
        if (vehicleUpdateInput.getVin() != null) {
            vehicle.setVin(vehicleUpdateInput.getVin());
        }
        if (vehicleUpdateInput.getCategoryName() != null){
            //TODO obadac jak w thymeleafie zrobic walidacje na kategorie
        }

        return this.vehicleRepository.save(vehicle).getId();
    }

    public int createVehicle(VehicleCreateInput vehicleCreateInput) throws DataExistsException, NotFoundException {
        if (this.vehicleRepository.getVehicleByParameters(vehicleCreateInput.getMark(), vehicleCreateInput.getModel(), vehicleCreateInput.getPlateNumber(), vehicleCreateInput.getVin()).isPresent()) {
            throw new DataExistsException("Vehicle with typed data is already in database.");
        }
        Vehicle vehicle = new Vehicle(vehicleCreateInput);
        if (vehicleCreateInput.getCategoryName() != null) {
            Category category = categoryService.getCategoryByNameAndType(vehicleCreateInput.getCategoryName(), "THEORY");
            if (category == null) {
                throw new NotFoundException("Typed category was not found.");
            }
            vehicle.setCategory(category);
        }
        return this.vehicleRepository.save(vehicle).getId();
    }

    public Vehicle getVehicle(int vehicleId) {
        Optional<Vehicle> result = this.vehicleRepository.findById(vehicleId);
        if (!result.isPresent()) {
            throw new NotFoundException("Vehicle with id = " + vehicleId + " has not been found");
        }
        return result.get();
    }

    public VehicleOutput getVehicleDetails(int vehicleId) throws NotFoundException {
        return new VehicleOutput(this.getVehicle(vehicleId));
    }

    public List<VehicleOutput> getVehicleDetailsList() {
        List<Vehicle> vehicles = this.vehicleRepository.findAll();
        List<VehicleOutput> vehicleOutputs = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehicleOutputs.add(new VehicleOutput(vehicle));
        }

        return vehicleOutputs;
    }

}
