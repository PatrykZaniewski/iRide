package iRide.controller;

import iRide.service.User.UserService;
import iRide.service.User.model.input.UserCreateInput;
import iRide.service.User.model.output.UserCreateOutput;
import iRide.service.User.model.output.UserListAdminOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String getUsers(Model model){
        List<UserListAdminOutput> userListAdminOutputs = this.userService.getUserListAdminOutput();
        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Konto zostało usunięte pomyślnie.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Wybrane konto zostało już usunięte.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Konto o wybranym id nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }
        model.addAttribute("userListAdminOutputs", userListAdminOutputs);
        return "admin/users";
    }

    @PostMapping(value = "/create")
    public String createUser(RedirectAttributes redirectAttributes, @ModelAttribute UserCreateInput userCreateInput){
        this.userService.createUser(userCreateInput);
        return "redirect:/user";
    }

    @DeleteMapping(value = "/{id}/{group}")
    public String deleteUser(RedirectAttributes redirectAttributes, @PathVariable int id, @PathVariable String group) {
        try {
            this.userService.deleteById(id, mapGroup(group));
            redirectAttributes.addFlashAttribute("code", 101);
        } catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/user";
    }

    @GetMapping(value = "/create")
    public String getCreateUser(Model model){
        UserCreateOutput userCreateOutput = this.userService.getCreateUser();
        model.addAttribute("userCreateOutput", userCreateOutput);

        return "admin/user_create";
    }

    private String mapGroup(String group){
        switch (group){
            case "Administrator":
                return "ADMIN";
            case "Kursant":
                return "STUDENT";
            case "Instruktor":
                return "INSTRUCTOR";
            default:
                return "UNKNOWN";
        }
    }
}
