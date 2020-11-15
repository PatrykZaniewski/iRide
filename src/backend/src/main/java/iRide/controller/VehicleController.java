package iRide.controller;

import iRide.service.Vehicle.VehicleService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/vehicle")
@Validated
public class VehicleController {

    public final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
        this.vehicleService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> createOne(@Valid @RequestBody VehicleCreateInput vehicleCreateInput) {
        try {
            int id = this.vehicleService.createOne(vehicleCreateInput);
            return ResponseEntity.ok(id);
        } catch (DataExistsException | NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
