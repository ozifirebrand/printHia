package ozi.app.printer.data.dtos.requests;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class OrderCreationRequest {

    private String imageUrl;

    private double size;

    private int quantity;

    private LocalDateTime orderDate= LocalDateTime.now();
}
