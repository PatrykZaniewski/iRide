package iRide.service.Vehicle;

import iRide.model.Category;
import iRide.model.Instructor;
import iRide.model.Vehicle;
import iRide.repository.VehicleRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.service.Vehicle.model.input.VehicleUpdateInput;
import iRide.service.Vehicle.model.output.VehicleAdminOutput;
import iRide.service.Vehicle.model.output.VehicleListAdminOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CategoryService categoryService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, CategoryService categoryService) {
        this.vehicleRepository = vehicleRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public int deleteVehicle(int id) {
        Vehicle vehicle = this.getVehicle(id);
        vehicleRepository.delete(vehicle);
        return id;
    }

    public List<VehicleListAdminOutput> getVehicleListAdminOutput() {
        List<Vehicle> vehicles = this.vehicleRepository.findAll();
        List<VehicleListAdminOutput> vehicleListAdminOutputs = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            VehicleListAdminOutput vehicleListAdminOutput = new VehicleListAdminOutput();

            vehicleListAdminOutput.setId(vehicle.getId());
            vehicleListAdminOutput.setMark(vehicle.getMark());
            vehicleListAdminOutput.setModel(vehicle.getModel());
            vehicleListAdminOutput.setCategory(vehicle.getCategory().getCategoryName());

            Map<Integer, String> instructors = new HashMap<>();
            for (Instructor instructor : vehicle.getInstructors()) {
                instructors.put(instructor.getId(), instructor.getLastname() + " " + instructor.getFirstname());
            }
            vehicleListAdminOutput.setInstructors(instructors);
            vehicleListAdminOutputs.add(vehicleListAdminOutput);
        }
        return vehicleListAdminOutputs;
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
        if (vehicleUpdateInput.getCategoryName() != null) {
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

    public VehicleAdminOutput getVehicleDetails(int vehicleId) throws NotFoundException {
        Vehicle vehicle = this.getVehicle(vehicleId);
        VehicleAdminOutput vehicleAdminOutput = new VehicleAdminOutput();

        vehicleAdminOutput.setId(vehicle.getId());
        vehicleAdminOutput.setMark(vehicle.getMark());
        vehicleAdminOutput.setModel(vehicle.getModel());
        vehicleAdminOutput.setPlateNumber(vehicle.getPlateNumber());
        vehicleAdminOutput.setVin(vehicle.getVin());
        vehicleAdminOutput.setCategory(vehicle.getCategory().getCategoryName());

        Map<Integer, String> instructors = new HashMap<>();
        for (Instructor instructor : vehicle.getInstructors()) {
            instructors.put(instructor.getId(), instructor.getFirstname() + " " + instructor.getLastname());
        }
        vehicleAdminOutput.setInstructors(instructors);

        return vehicleAdminOutput;
    }

//    public List<VehicleAdminOutput> getVehicleDetailsList() {
//        List<Vehicle> vehicles = this.vehicleRepository.findAll();
//        List<VehicleAdminOutput> vehicleAdminOutputs = new ArrayList<>();
//
//        for (Vehicle vehicle : vehicles) {
//            vehicleAdminOutputs.add(new VehicleAdminOutput(vehicle));
//        }
//
//        return vehicleAdminOutputs;
//    }

}
