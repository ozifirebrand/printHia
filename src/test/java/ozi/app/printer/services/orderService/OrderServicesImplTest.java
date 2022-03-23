package ozi.app.printer.services.orderService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServicesImplTest {
    @Autowired
    private OrderServices orderServices;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder() {
        OrderCreationRequest request = new OrderCreationRequest();
        request.setOrderDate(LocalDateTime.now());
        request.setSize(12.5);
        request.setImageUrl("imageUrl");
        request.setQuantity(1);
        OrderCreationResponse response =orderServices.createOrder(request);
        assertThat(response).isNotNull();
        assertThat(response.getOrderDate()).isEqualTo(request.getOrderDate());
        assertThat(response.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(response.getSize()).isEqualTo(request.getSize());
        assertThat(response.getImageUrl()).isEqualTo(request.getImageUrl());
        assertThat(response.isOrdered()).isTrue();
    }

    @Test
    public void getOrderById() {
    }

    @Test
    public void clearAllOrders() {
    }

    @Test
    public void deleteOrderById() {
    }

    @Test
    public void getAllOrders() {
    }

    @Test
    public void getOrdersByUsername() {
    }

    @Test
    public void getOrdersByDate() {
    }

    @Test
    public void getOrdersByStatus() {
    }
}