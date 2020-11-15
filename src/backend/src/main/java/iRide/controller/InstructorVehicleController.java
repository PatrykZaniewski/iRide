package iRide.controller;

import iRide.service.InstructorVehicle.InstructorVehicleService;
import iRide.service.InstructorVehicle.model.input.InstructorVehicleInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/instructorVehicle")
public class InstructorVehicleController {

    private final InstructorVehicleService instructorVehicleService;

    @Autowired
    public InstructorVehicleController(InstructorVehicleService instructorVehicleService) {
        this.instructorVehicleService = instructorVehicleService;
    }

    @PostMapping("/{instructorId}")
    public ResponseEntity<Object> assign(@PathVariable int instructorId, @RequestBody List<InstructorVehicleInput> instructorCategoryInputs) {
        int result = this.instructorVehicleService.assignInstructorsToVehicle(instructorCategoryInputs, instructorId);
        return ResponseEntity.ok(result);
    }

}
