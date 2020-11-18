package iRide.service.InstructorVehicle;

import iRide.model.Instructor;
import iRide.model.InstructorVehicle;
import iRide.model.Vehicle;
import iRide.repository.InstructorVehicleRepository;
import iRide.service.Instructor.InstructorService;
import iRide.service.InstructorVehicle.model.input.InstructorVehicleInput;
import iRide.service.Vehicle.VehicleService;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstructorVehicleService {

    private final InstructorVehicleRepository instructorVehicleRepository;
    private final InstructorService instructorService;
    private final VehicleService vehicleService;

    @Autowired
    public InstructorVehicleService(InstructorVehicleRepository instructorVehicleRepository, InstructorService instructorService, VehicleService vehicleService) {
        this.instructorVehicleRepository = instructorVehicleRepository;
        this.instructorService = instructorService;
        this.vehicleService = vehicleService;
    }

    @Transactional
    public int assignInstructorsToVehicle(List<InstructorVehicleInput> instructorCategoryInputs, int vehicleId) {
        List<Integer> existingVehicleInstructors = this.instructorVehicleRepository.getIdsOfVehicleInstructors(vehicleId).isPresent() ? this.instructorVehicleRepository.getIdsOfVehicleInstructors(vehicleId).get() : Collections.emptyList();
        Vehicle vehicle = null;
        List<InstructorVehicle> vehicleInstructors = new ArrayList<>();
        try {
            vehicle = vehicleService.getVehicle(vehicleId);
            for (InstructorVehicleInput instructorVehicleInput : instructorCategoryInputs) {
                Instructor instructor = instructorService.getInstructor(instructorVehicleInput.getInstructorId());
                if (!existingVehicleInstructors.contains(instructor.getId())) {
                    InstructorVehicle instructorVehicle = new InstructorVehicle(instructor, vehicle);
                    vehicleInstructors.add(instructorVehicle);
                    //TODO zbierac info o istniejacych i dawac exception jesli nie istnieje id
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        this.instructorVehicleRepository.deleteVehicleInstructors(vehicleId);
        for (InstructorVehicle instructorVehicle : vehicleInstructors) {
            this.instructorVehicleRepository.save(instructorVehicle);
        }
        return 1;
    }
}
