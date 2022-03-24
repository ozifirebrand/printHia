package ozi.app.printer.data.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    private PrintOrder order;
    PrintOrder savedOrder;
    @BeforeEach
    void setUp() {
        order = new PrintOrder();
        order.setOrdered(true);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setOrderDate(LocalDateTime.now());
        order.setQuantity(1);
        order.setSize(43);
        order.setImageUrl("myimageurl.com");
        order.setDeliveryDate(order.getOrderDate().plusDays(3) );

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder(){
        //given...
        //when
        savedOrder = repository.save(order);

        //assert
        assertThat(savedOrder.getOrderDate()).isEqualTo(order.getOrderDate());
        assertThat(savedOrder.getOrderStatus()).isEqualTo(order.getOrderStatus());
        assertThat(savedOrder.getQuantity()).isEqualTo(order.getQuantity());
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getSize()).isEqualTo(order.getSize());
        assertThat(savedOrder.getDeliveryDate()).isNotNull();
        assertThat(savedOrder.getImageUrl()).isEqualTo(order.getImageUrl());
    }
    @Test
    void findPrintOrderByOrderDate() {
        savedOrder = repository.save(order);

    }
}