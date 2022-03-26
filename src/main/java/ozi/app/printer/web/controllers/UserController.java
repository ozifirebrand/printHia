package ozi.app.printer.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.services.orderService.OrderServices;
import ozi.app.printer.services.userService.UserServices;

@Controller
@RequestMapping("api/user/print")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private OrderServices orderServices;

    @PostMapping("/order")
    public ResponseEntity<?> makePrintOrder(@RequestBody OrderCreationRequest request)
            throws BusinessLogicException {
        OrderCreationResponse response = orderServices.createOrder(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> viewOrder(@PathVariable String id){

        return null;
    }

    @GetMapping("/orders")
    public ResponseEntity<?> viewAllOrders(@RequestParam String id){

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


