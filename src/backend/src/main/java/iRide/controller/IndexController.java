package iRide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping("")
    public String getIndex(Model model, Authentication authentication){
        if (authentication != null) {
            model.addAttribute("accountRole", String.valueOf(authentication.getAuthorities().toArray()[0]));
        }
        return "index";
    }
}
