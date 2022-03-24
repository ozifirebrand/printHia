package ozi.app.printer.mapper;

import ozi.app.printer.data.dtos.requests.*;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.models.PrintUser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Mapper {
    public static PrintUser map (UserCreationRequest request){
        PrintUser user = new PrintUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;

    }

    public static UserCreationResponse map(PrintUser user){
        UserCreationResponse response = new UserCreationResponse();

        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setId(user.getId());
        return response;
    }

    public static PrintOrder map(OrderCreationRequest request){
        PrintOrder order = new PrintOrder();
        order.setOrderDate(request.getOrderDate());
        order.setImageUrl(request.getImageUrl());
        order.setSize(request.getSize());
        order.setQuantity(request.getQuantity());
        return order;
    }

    public static OrderCreationResponse map(PrintOrder order){

        OrderCreationResponse response = new OrderCreationResponse();
        response.setId(order.getId());
        LocalDateTime truncatedTime = order.getOrderDate().truncatedTo(ChronoUnit.SECONDS);
        response.setOrderDate(truncatedTime);
        response.setQuantity(order.getQuantity());
        response.setDeliveryDate(order.getDeliveryDate());
        response.setSize(order.getSize());
        response.setImageUrl(order.getImageUrl());
        response.setOrderStatus(order.getOrderStatus());
        return response;
    }

    public static PrintAdmin map (AdminCreationRequest request){
        PrintAdmin admin = new PrintAdmin();
        admin.setEmail(request.getEmail());
        admin.setFirstName(request.getFirstName());
        admin.setUsername(request.getUsername());
        admin.setLastName(request.getLastName());
        return admin;
    }

    public static AdminCreationResponse map (PrintAdmin admin){
        AdminCreationResponse response = new AdminCreationResponse();
        response.setId(admin.getId());
        response.setUsername(admin.getUsername());
        response.setEmail(admin.getEmail());
        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        return response;
    }
}
