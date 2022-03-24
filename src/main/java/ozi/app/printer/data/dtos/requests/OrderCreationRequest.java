package ozi.app.printer.data.dtos.requests;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class OrderCreationRequest {

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime orderDate;
}
