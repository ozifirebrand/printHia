package ozi.app.printer.data.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    private PrintOrder order;
    private PrintOrder savedOrder;
    private PrintOrder savedOrder1;

    @BeforeEach
    void setUp() {
        order = new PrintOrder();
        order.setOrdered(true);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        order.setQuantity(1);
        order.setSize(43);
        order.setImageUrl("myimageurl.com");
        order.setDeliveryDate(order.getOrderDate().plusDays(3));
        savedOrder =repository.save(order);

        PrintOrder order1 = new PrintOrder();
        order1.setOrdered(true);
        order1.setOrderStatus(OrderStatus.ORDERED);
        order1.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        order1.setQuantity(12);
        order1.setSize(15);
        order1.setImageUrl("myigeurl.com");
        order1.setDeliveryDate(order.getOrderDate().plusDays(3));
        savedOrder1= repository.save(order1);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder() {
        //given...
        //when


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
    public void findPrintOrderByOrderDate() {
        //given...@setup

        //when
        List<PrintOrder> ordersByDate = repository.findByOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));

        //assert
        assertThat(ordersByDate.size()).isEqualTo(2);
        assertThat(ordersByDate.get(0).getOrderDate()).isEqualTo(ordersByDate.get(1).getOrderDate());
        assertThat(ordersByDate.get(0).getId()).isEqualTo(savedOrder.getId());
        assertThat(ordersByDate.get(1).getId()).isEqualTo(savedOrder1.getId());

    }

    @Mock
    private OrderRepository mockRepository;
    @Test
    public void findPrintOrderByUserId(){
        //given @setup...

        List<PrintOrder> orders = new ArrayList<>();

        //when
        when(mockRepository.findPrintOrderByUserId("437uai82798")).thenReturn(orders);
        orders = mockRepository.findPrintOrderByUserId("437uai82798");
        verify(mockRepository,times(1));

    }
}