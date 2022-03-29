package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
class OrderServicesImplTest {
    @Mock
    private OrderServices mockOrderServices;

    private OrderCreationRequest orderCreationRequest;

    @BeforeEach
    void setUp() {
        orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        orderCreationRequest.setSize(14.0);
        orderCreationRequest.setQuantity(12);
        orderCreationRequest.setImageUrl("imageurl.com");
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder() throws BusinessLogicException {
        //given

        OrderCreationResponse orderCreationResponse = new OrderCreationResponse();

        //when
        when(mockOrderServices.createOrder(orderCreationRequest)).thenReturn(orderCreationResponse);
        orderCreationResponse = mockOrderServices.createOrder(orderCreationRequest);
        verify(mockOrderServices, times(1));
    }

    @Autowired
    private OrderServices orderServices;

    @Test
    public void testIncompleteDetails_ThrowException(){

        //given
        orderCreationRequest.setUserId(null);

        //when
        //assert
        assertThatThrownBy
                (()->orderServices.createOrder(orderCreationRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("The given details are incomplete");
    }

    @Test
    public void deleteOrderById() throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        boolean orderIsDeleted = orderServices.deleteOrderById(response.getId());

        //assert
        assertThat(orderIsDeleted).isTrue();

    }

    @Test
    public void deleteOrderByUserId() {
    }

    @Test
    public void testInvalidId_ThrowException(){


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