package ozi.app.printer.data.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class PrintOrder {
    @Id
    private String id;

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime deliveryDate;

    private boolean hasOrdered;

    private OrderStatus orderStatus;
}
