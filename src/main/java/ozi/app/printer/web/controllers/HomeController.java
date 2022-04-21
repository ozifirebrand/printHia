package ozi.app.printer.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.services.orderService.OrderServices;

@RestController
@RequestMapping("/home")

public class HomeController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping("/welcome-page")
    public OrderCreationResponse makeOrder(@RequestBody OrderCreationRequest request) throws BusinessLogicException {
        return orderServices.createOrder(request);
    }
}
