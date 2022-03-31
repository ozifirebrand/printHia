package ozi.app.printer.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.services.adminService.AdminServices;
import ozi.app.printer.services.orderService.OrderServices;
import ozi.app.printer.services.userService.UserServices;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/admin/print")
public class AdminController {
    @Autowired
    private AdminServices adminServices;

    @Autowired
    private OrderServices orderServices;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationRequest orderCreationRequest){
        try {
            OrderCreationResponse orderCreationResponse = orderServices.createOrder(orderCreationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderCreationResponse);
        }
        catch (BusinessLogicException businessLogicException){
            return ResponseEntity.badRequest().body(businessLogicException.getMessage());
        }
    }
}