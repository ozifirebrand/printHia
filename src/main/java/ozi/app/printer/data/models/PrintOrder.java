package ozi.app.printer.data.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class PrintOrder {
    @Id
    private String id;

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private boolean hasOrdered= true;

    private OrderStatus orderStatus;
}
