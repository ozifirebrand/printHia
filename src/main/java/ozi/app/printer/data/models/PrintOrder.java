package ozi.app.printer.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class PrintOrder {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(strategy = "uuid", name = "system-uuid")
    private String id;

    private String imageUrl;

    private double size;

    private int quantity;

    private double price;

    @JsonFormat(pattern = "yy/MM/dd")
    private LocalDateTime orderDate;

    @JsonFormat(pattern = "yy/MM/dd")
    private LocalDateTime deliveryDate;

    private boolean ordered;

    private String userId;

    private OrderStatus orderStatus;
}
