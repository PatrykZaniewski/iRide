package iRide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping("")
    public String getLogin(Model model, Authentication authentication , @RequestParam(name = "error", required = false) String errorCode){
        if (authentication != null){
            return "redirect:/";
        }
        if (errorCode != null){
            String message;
            switch (errorCode){
                case "101":
                    message = "Nieprawidłowe dane logowania.";
                    break;
                case "102":
                    message = "Twoje konto zostało zablokowane.";
                    break;
                case "103":
                    message = "Twoje konto nie zostało aktywowane.";
                    break;
                default:
                    message = "Nieznany błąd logowania. Spróbuj później.";
                    break;
            }
            model.addAttribute("message", message);
        }
        return "login";
    }

    @GetMapping("/change_password")
    public String getChangePassword(Model model){
        //        model.addAttribute("message", "test");
        return "change_password";
    }

    @GetMapping("/reset")
    public String getReset(Model model, Authentication authentication , @RequestParam(name = "error", required = false) String errorCode){
//        model.addAttribute("message", "test");
        return "login_reset";
    }

    @GetMapping("/reset/new_password")
    public String getResetNewPassword(Model model, Authentication authentication , @RequestParam(name = "error", required = false) String errorCode){
//        model.addAttribute("message", "test");
        return "login_reset_new_password";
    }

}
