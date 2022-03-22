package ozi.app.printer.data.dtos.responses;

import lombok.Data;
import ozi.app.printer.data.models.OrderStatus;
import java.time.LocalDateTime;
@Data
public class OrderCreationResponse {

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private final boolean hasOrdered= true;

    private OrderStatus orderStatus;

}
