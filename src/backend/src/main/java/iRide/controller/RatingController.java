package iRide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/rating")
public class RatingController {

    @GetMapping("/add")
    public String getChangePassword(Model model){
        //        model.addAttribute("message", "test");
        return "student/course_add_review";
    }

    @GetMapping("/")
    public String getList(Model model){
        return "admin/instructor_ratings_list";
    }

    @GetMapping("/{id}")
    public String getList(Model model, String id){
        return "admin/instructor_rating_details";
    }

}
