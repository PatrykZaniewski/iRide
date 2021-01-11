package iRide.service.Vehicle;

import iRide.model.Category;
import iRide.model.Instructor;
import iRide.model.Vehicle;
import iRide.repository.VehicleRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Instructor.InstructorService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.service.Vehicle.model.input.VehicleUpdateInput;
import iRide.service.Vehicle.model.output.*;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CategoryService categoryService;
    private final InstructorService instructorService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, CategoryService categoryService, @Lazy InstructorService instructorService) {
        this.vehicleRepository = vehicleRepository;
        this.categoryService = categoryService;
        this.instructorService = instructorService;
    }

    public VehicleCreateOutput getCreateVehicle(){
        VehicleCreateOutput vehicleCreateOutput = new VehicleCreateOutput();
        vehicleCreateOutput.setCategories(this.categoryService.getCategoriesForVehicles());

        return vehicleCreateOutput;
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

    public List<VehicleListInstructorOutput> getVehicleListInstructorOutput(int userId) {
        List<Vehicle> vehicles = this.vehicleRepository.findAll();
        List<VehicleListInstructorOutput> vehicleListInstructorOutputs = new ArrayList<>();
        Instructor instructor = this.instructorService.getInstructorByUserId(userId);
        for (Vehicle vehicle: vehicles){
            for (Instructor i: vehicle.getInstructors()){
                if(i.getId() == instructor.getId()){
                    VehicleListInstructorOutput vehicleListInstructorOutput = new VehicleListInstructorOutput();

                    vehicleListInstructorOutput.setId(vehicle.getId());
                    vehicleListInstructorOutput.setMark(vehicle.getMark());
                    vehicleListInstructorOutput.setModel(vehicle.getModel());
                    vehicleListInstructorOutput.setCategory(vehicle.getCategory().getCategoryName());

                    vehicleListInstructorOutputs.add(vehicleListInstructorOutput);
                }
            }
        }
        return vehicleListInstructorOutputs;
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

    public void createVehicle(VehicleCreateInput vehicleCreateInput) throws DataExistsException, NotFoundException {
        if (this.vehicleRepository.getVehicleByParameters("%", "%", "%", vehicleCreateInput.getVin()).isPresent()) {
            throw new DataExistsException("Vehicle with typed vin is already in database.");
        }
        Vehicle vehicle = new Vehicle(vehicleCreateInput);
        if (vehicleCreateInput.getCategoryId() != 0) {
            Category category = categoryService.getCategory(vehicleCreateInput.getCategoryId());
            if (category == null) {
                throw new NotFoundException("Typed category was not found.");
            }
            vehicle.setCategory(category);
        }
        this.vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicle(int vehicleId) {
        Optional<Vehicle> result = this.vehicleRepository.findById(vehicleId);
        if (!result.isPresent()) {
            throw new NotFoundException("Vehicle with id = " + vehicleId + " has not been found");
        }
        return result.get();
    }

    public VehicleAdminOutput getVehicleDetailsAsAdmin(int vehicleId) throws NotFoundException {
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

    public VehicleInstructorOutput getVehicleDetailsAsInstructor(int vehicleId, int userId) throws NotFoundException {
        Vehicle requestedVehicle = null;
        VehicleInstructorOutput vehicleInstructorOutput = new VehicleInstructorOutput();

        for(Vehicle vehicle : this.instructorService.getInstructorByUserId(userId).getVehicles()){
            if(vehicle.getId() == vehicleId){
                requestedVehicle = vehicle;
                break;
            }
        }

        if (requestedVehicle == null){
            throw new NotFoundException("Vehicle not found");
        }

        vehicleInstructorOutput.setId(requestedVehicle.getId());
        vehicleInstructorOutput.setMark(requestedVehicle.getMark());
        vehicleInstructorOutput.setModel(requestedVehicle.getModel());
        vehicleInstructorOutput.setPlateNumber(requestedVehicle.getPlateNumber());
        vehicleInstructorOutput.setVin(requestedVehicle.getVin());
        vehicleInstructorOutput.setCategory(requestedVehicle.getCategory().getCategoryName());

        Map<Integer, String> instructors = new HashMap<>();
        for (Instructor instructor : requestedVehicle.getInstructors()) {
            instructors.put(instructor.getId(), instructor.getFirstname() + " " + instructor.getLastname());
        }
        vehicleInstructorOutput.setInstructors(instructors);

        return vehicleInstructorOutput;
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
