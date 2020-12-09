package iRide.controller;

import iRide.service.Vehicle.VehicleService;
import iRide.service.Vehicle.model.output.VehicleAdminOutput;
import iRide.service.Vehicle.model.output.VehicleListAdminOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getVehicles(Model model){
        List<VehicleListAdminOutput> vehicleListAdminOutputs = this.vehicleService.getVehicleListAdminOutput();
        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Pojazd został usunięty pomyślnie.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Wybrany pojazd został już usunięty.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Pojazd o wybranym id nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }
        model.addAttribute("vehicleListAdminOutputs", vehicleListAdminOutputs);
        return "admin/vehicles";
    }

    @GetMapping(value = "/{id}")
    public String getVehicleDetails(Model model, @PathVariable int id, RedirectAttributes redirectAttributes){
        try {
            VehicleAdminOutput vehicleAdminOutput = this.vehicleService.getVehicleDetails(id);
            model.addAttribute("vehicleAdminOutput", vehicleAdminOutput);
            return "admin/vehicle_details_admin";
        }
        catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 202);
            return "redirect:/vehicle";
        }
    }

    @DeleteMapping(value = "/{id}")
    public String deleteVehicle(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            this.vehicleService.deleteVehicle(id);
            redirectAttributes.addFlashAttribute("code", 101);
        } catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/vehicle";
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
