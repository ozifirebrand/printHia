package ozi.app.printer.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/home")
public class HomeController {
    @RequestMapping("/")
    public String welcome(){
        return "home";
    }
}
