package ozi.app.printer.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.models.OrderStatus;
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
    private UserServices userServices;

    @Autowired
    private OrderServices orderServices;

    @PostMapping("/order/status")
    public ResponseEntity<?> changeOrderStatus(@RequestParam OrderStatus status){

        return null;
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> editProfile(@RequestBody AdminCreationRequest request){

        return null;
    }

    @PatchMapping("/date")
    public ResponseEntity<?> updateDeliveryDate(@RequestParam LocalDateTime date){

        return null;
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestParam String id){

        return null;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> viewUserDetails(@PathVariable String id){

        return null;
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> viewUsers(){

        return null;
    }

    @GetMapping("/orders/{date}")
    public ResponseEntity<?> getOrdersByDate(@PathVariable LocalDateTime date){

        return null;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable String id){

        return null;
    }

    @GetMapping("/orders/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable OrderStatus status){

        return null;
    }
}