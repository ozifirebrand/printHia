package ozi.app.printer.data.dtos.requests;

import lombok.Data;

@Data
public class OrderUpdateRequest {

    private double size;

    private int quantity;
}
