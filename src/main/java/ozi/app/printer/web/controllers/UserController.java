package ozi.app.printer.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.services.userService.UserServices;

@Slf4j
@Controller
@RequestMapping("api/user/print")
public class UserController {
    @Autowired
    private UserServices userServices;


    @GetMapping("/orders{id}")
    public ResponseEntity<?> viewAllOrders(@PathVariable String id){

        return null;
    }

        @GetMapping("/profile/{id}")
    public ResponseEntity<?> viewProfileDetails(@PathVariable String id){

        return null;
    }

    @PatchMapping("/profile/edit")
    public ResponseEntity<?> editProfileDetails(@RequestParam String id, UserCreationRequest request){

        return null;
    }
}


