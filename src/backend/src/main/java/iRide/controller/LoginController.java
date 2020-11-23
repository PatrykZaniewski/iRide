package iRide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping("")
    public String getLogin(Model model, Authentication authentication){
        if (authentication != null){
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/error")
    public String getLoginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
