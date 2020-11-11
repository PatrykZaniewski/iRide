package iRide.controller;

import iRide.service.Vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    public final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id){
        this.vehicleService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
