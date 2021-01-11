package iRide.controller;

import iRide.config.AuthenticationUserDetails;
import iRide.service.Course.model.output.CourseAdminOutput;
import iRide.service.Course.model.output.CourseInstructorOutput;
import iRide.service.Course.model.output.CourseStudentOutput;
import iRide.service.Vehicle.VehicleService;
import iRide.service.Vehicle.model.input.VehicleCreateInput;
import iRide.service.Vehicle.model.output.*;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String getVehicles(Model model, @AuthenticationPrincipal AuthenticationUserDetails authenticationUserDetails){
        String group = authenticationUserDetails.getAuthorities().get(0).toString();
        int userId = authenticationUserDetails.getId();

        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Pojazd został usunięty pomyślnie.");
                    break;
                case 102:
                    model.addAttribute("infoMessage", "Pojazd został dodany pomyślnie.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Wybrany pojazd został już usunięty.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Pojazd o wybranym id nie istnieje.");
                    break;
                case 203:
                    model.addAttribute("infoError", "Pojazd z wybranym numerem vin istnieje.");
                    break;
                case 204:
                    model.addAttribute("infoError", "Wybrana kategoria nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }

        switch (group){
            case "ADMIN":
                List<VehicleListAdminOutput> vehicleListAdminOutputs = this.vehicleService.getVehicleListAdminOutput();
                model.addAttribute("vehicleListAdminOutputs", vehicleListAdminOutputs);
                return "admin/vehicles";
            case "INSTRUCTOR":
                List<VehicleListInstructorOutput> vehicleListInstructorOutputs = this.vehicleService.getVehicleListInstructorOutput(userId);
                model.addAttribute("vehicleListInstructorOutputs", vehicleListInstructorOutputs);
                return "instructor/vehicles";
            default:
                //TODO do 403?
                return null;
        }
    }

    @GetMapping(value = "/{id}")
    public String getVehicleDetails(Model model, @PathVariable int id, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthenticationUserDetails authenticationUserDetails){
        String group = authenticationUserDetails.getAuthorities().get(0).toString();
        int userId = authenticationUserDetails.getId();

        try {
            switch (group){
                case "ADMIN":
                    VehicleAdminOutput vehicleAdminOutput = this.vehicleService.getVehicleDetailsAsAdmin(id);
                    model.addAttribute("vehicleAdminOutput", vehicleAdminOutput);
                    return "admin/vehicle_details_admin";
                case "INSTRUCTOR":
                    VehicleInstructorOutput vehicleInstructorOutput = this.vehicleService.getVehicleDetailsAsInstructor(id, userId);
                    model.addAttribute("vehicleInstructorOutput", vehicleInstructorOutput);
                    return "instructor/vehicle_details_instructor";
                default:
                    //TODO do 403?
                    return null;
            }
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

    @PostMapping(value = "/create")
    public String createOne(@ModelAttribute VehicleCreateInput vehicleCreateInput, RedirectAttributes redirectAttributes) {
        try {
            this.vehicleService.createVehicle(vehicleCreateInput);
            redirectAttributes.addFlashAttribute("code", 102);
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("code", 204);
        }
        catch (DataExistsException e){
            redirectAttributes.addFlashAttribute("code", 203);
        }
        return "redirect:/vehicle";
    }

    @GetMapping(value = "/create")
    public String getCreateVehicle(Model model){
        VehicleCreateOutput vehicleCreateOutput = this.vehicleService.getCreateVehicle();
        model.addAttribute("vehicleCreateOutput", vehicleCreateOutput);

        return "admin/vehicle_create";
    }

    @ModelAttribute("accountRole")
    public String getAccountRole(Authentication authentication){
        if (authentication != null){
            return String.valueOf(authentication.getAuthorities().toArray()[0]);
        }
        return null;
    }

}
