package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
class OrderServicesImplTest {
    @Mock
    private OrderServices orderServices;


    private OrderCreationRequest orderCreationRequest;


    @BeforeEach
    void setUp() {
        orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        orderCreationRequest.setSize(14.0);
        orderCreationRequest.setQuantity(12);
        orderCreationRequest.setImageUrl("imageurl.com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);

        OrderCreationResponse orderCreationResponse = new OrderCreationResponse();

        //when
        when(orderServices.createOrder(orderCreationRequest)).thenReturn(orderCreationResponse);
        orderCreationResponse = orderServices.createOrder(orderCreationRequest);
        verify(orderServices, times(1));
        log.info("{}",orderServices.getAllOrders());
    }

    @Test
    public void deleteOrderById() {
    }

    @Test
    public void deleteOrderByUserId() {
    }

    @Test
    public void updateOrderDetails() {
    }

    @Test
    public void updateOrderStatus() {
    }

    @Test
    public void updateOrderDeliverDate() {
    }

    @Test
    public void deleteAllOrders() {
    }

    @Test
    public void getOrderById() {
    }

    @Test
    public void getOrdersByDate() {
    }

    @Test
    public void getOrdersByStatus() {
    }

    @Test
    public void getOrdersByUserId() {
    }

    @Test
    public void getAllOrders() {
    }
}