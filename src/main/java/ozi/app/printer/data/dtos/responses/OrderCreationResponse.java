package ozi.app.printer.data.dtos.responses;

import lombok.Data;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderCreationResponse {
    private String id;

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private final boolean ordered= true;

    private OrderStatus orderStatus;

    private List<PrintOrder> orders;

}
