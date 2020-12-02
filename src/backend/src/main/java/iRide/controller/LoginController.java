package iRide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping("")
    public String getLogin(HttpServletRequest request, Model model, Authentication authentication , @RequestHeader(name = "Error-Code", required = false) String errorCode){
        String test = request.getHeader("Error-Code");
        if (authentication != null){
            return "redirect:/";
        }
        if (errorCode != null){
            String message = null;
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

            }
            model.addAttribute("message", message);
        }
        return "login";
    }

    @GetMapping("/error")
    public String getLoginError(Model model, @RequestParam(name = "error_code", required = false) String errorCode){

        return "login";
    }
}
