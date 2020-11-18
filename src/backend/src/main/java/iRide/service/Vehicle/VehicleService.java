package iRide.service.Vehicle;

import iRide.model.Category;
import iRide.model.Vehicle;
import iRide.repository.VehicleRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Vehicle getVehicle(int id) {
        if (this.vehicleRepository.findById(id).isPresent()) {
            return this.vehicleRepository.findById(id).get();
        }
        return null;
    }
}