package iRide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

    @GetMapping(value = "")
    public String getMailbox(){
        return "/message";
    }

    @GetMapping(value = "/{id}")
    public String getMessage(@PathVariable String id){
        return "/message_view";
    }

    @GetMapping(value = "/send")
    public String getSendMessage(){
        return "/message_send";
    }
}
