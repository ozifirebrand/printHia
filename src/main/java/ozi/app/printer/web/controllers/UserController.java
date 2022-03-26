package ozi.app.printer.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;
import ozi.app.printer.services.orderService.OrderServices;
import ozi.app.printer.services.userService.UserServices;

@Slf4j
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
        try {
            OrderCreationResponse response = orderServices.createOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
        catch (OrderException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        /*model attributes are supposed to be sent here and not http responses.
        anyways, just try your hands or testing endpoints after which you are to
        refactor your codes to return views and not http responses
        */
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> viewOrder(@PathVariable String id) throws OrderException {
        try {
            OrderCreationResponse response = orderServices.getOrderById(id);
            return  ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (OrderException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
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


