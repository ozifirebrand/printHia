package ozi.app.printer.data.dtos.responses;

import ozi.app.printer.data.models.OrderStatus;
import java.time.LocalDateTime;

public class OrderCreationResponse {

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private final boolean hasOrdered= true;

    private OrderStatus orderStatus;

}
