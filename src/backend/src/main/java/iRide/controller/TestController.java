package iRide.controller;

import iRide.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping("/")
    public String showIndex(Model model){
        User user = new User();
        user.setAccountRole("ADMIN");
        model.addAttribute(user);
        return "/index";
    }
}
