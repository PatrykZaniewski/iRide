package iRide.controller;

import iRide.service.Instructor.model.output.InstructorListOutput;
import iRide.service.Vehicle.VehicleService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.service.Vehicle.model.output.VehicleListOutput;
import iRide.service.Vehicle.model.output.VehicleOutput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/vehicle")
public class VehicleController {

    public final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(value = "")
    public String getInstructors(Model model){
        List<VehicleListOutput> vehicleListOutputs = this.vehicleService.getVehicleListOutput();
        model.addAttribute("vehicleListOutputs", vehicleListOutputs);
        return "vehicles";
    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
//        this.vehicleService.deleteVehicle(id);
//        return new ResponseEntity<>(id, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/")
//    public ResponseEntity<Object> createOne(@RequestBody VehicleCreateInput vehicleCreateInput) {
//        try {
//            int id = this.vehicleService.createVehicle(vehicleCreateInput);
//            return ResponseEntity.ok(id);
//        } catch (DataExistsException | NotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping(value = "/{vehicleId}")
//    public ResponseEntity<VehicleOutput> getVehicleDetails(@PathVariable int vehicleId){
//        return ResponseEntity.ok(this.vehicleService.getVehicleDetails(vehicleId));
//    }

    @ModelAttribute("accountRole")
    public String getAccountRole(Authentication authentication){
        if (authentication != null){
            return String.valueOf(authentication.getAuthorities().toArray()[0]);
        }
        return null;
    }

}
