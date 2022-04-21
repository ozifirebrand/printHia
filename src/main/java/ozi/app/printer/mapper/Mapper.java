package ozi.app.printer.mapper;

import ozi.app.printer.data.dtos.requests.*;
import ozi.app.printer.data.dtos.responses.*;
import ozi.app.printer.data.models.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Mapper {
    public static PrintUser map (UserCreationRequest request){
        PrintUser user = new PrintUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setOrders(new ArrayList<>());
        return user;

    }

    public static UserCreationResponse map(PrintUser user){
        user.setRole(Role.USER);
        UserCreationResponse response = new UserCreationResponse();

        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setId(user.getId());
        response.setRole(user.getRole());
        response.setOrders(user.getOrders());
        return response;
    }

    public static PrintOrder map(OrderCreationRequest request){
        PrintOrder order = new PrintOrder();
        order.setImageUrl(request.getImageUrl());
        order.setSize(request.getSize());
        order.setQuantity(request.getQuantity());
        order.setUserId(request.getUserId());
        return order;
    }

    public static OrderCreationResponse map(PrintOrder order){

        OrderCreationResponse response = new OrderCreationResponse();
        response.setId(order.getId());
        LocalDateTime truncatedTime = order.getOrderDate().truncatedTo(ChronoUnit.DAYS);
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
        admin.setLastName(request.getLastName());
        return admin;
    }

    public static AdminCreationResponse map (PrintAdmin admin){
        admin.setRole(Role.ADMIN);
        AdminCreationResponse response = new AdminCreationResponse();
        response.setId(admin.getId());
        response.setEmail(admin.getEmail());
        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setRole(admin.getRole());
        return response;
    }
}
