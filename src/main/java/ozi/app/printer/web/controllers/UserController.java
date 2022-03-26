package ozi.app.printer.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.services.orderService.OrderServices;
import ozi.app.printer.services.userService.UserServices;

@Controller
@RequestMapping("api/user/print")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private OrderServices orderServices;

    @GetMapping("/order")
    public ResponseEntity<?> makePrintOrder(@RequestBody OrderCreationRequest request){

        return null;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> viewOrder(@PathVariable String id){

        return null;
    }

    @GetMapping("/orders")
    public ResponseEntity<?> viewAllOrders(@RequestParam String id){

        return null;
    }

    @PostMapping("/profile")
    public ResponseEntity<?> viewProfileDetails(@RequestParam String id){

        return null;
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<?> editProfileDetails(@RequestParam String id, UserCreationRequest request){

        return null;
    }


}


