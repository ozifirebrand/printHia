package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.OrderUpdateRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
class OrderServicesImplTest {

    @Autowired
    private OrderServices orderServices;

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
        mockOrderServices.createOrder(orderCreationRequest);
        verify(mockOrderServices, times(1)).createOrder(orderCreationRequest);
    }

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
        OrderCreationResponse response =orderServices.createOrder(orderCreationRequest);

        //when
        boolean orderIsDeleted = orderServices.deleteOrderById(response.getId());

        //assert
        assertThat(orderIsDeleted).isTrue();

    }

    @Test
    public void testInvalidId_ThrowException() {
        //given
        //when
        //assert
        assertThatThrownBy(()->orderServices
                .deleteOrderById("mine"))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("No such order exists!");

    }

    @Test
    public void deleteOrderByUserId() {
        //given
        String userId = "aUserId";
        //when
        when(mockOrderServices.deleteOrderByUserId(userId)).thenReturn(true);
        boolean isDeleted = mockOrderServices.deleteOrderByUserId(userId);
        verify(mockOrderServices, times(1)).deleteOrderByUserId(userId);
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void testInvalidUserId_ThrowException() {
        //given
        //when
        boolean isDeleted = orderServices.deleteOrderByUserId("anInvalidId");
        //assert
        assertThat(isDeleted).isFalse();
    }

    @Test
    public void updateOrderDetails() throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
        orderUpdateRequest.setQuantity(9);
        orderUpdateRequest.setSize(22);
        OrderCreationResponse response1 = orderServices.updateOrderDetails(response.getId(), orderUpdateRequest);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(9);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(22);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void if_updateDetailIsNullOrEmpty_DetailDoesNotChangeInDB()
            throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
        orderUpdateRequest.setQuantity(9);
        OrderCreationResponse response1 = orderServices.updateOrderDetails(response.getId(), orderUpdateRequest);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(9);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(14);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void test_if_updateDetailIsNullOrEmpty_DetailDoesNotChangeInDB()
            throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        OrderUpdateRequest orderUpdateRequest2 = new OrderUpdateRequest();
        orderUpdateRequest2.setSize(88);
        OrderCreationResponse response1 = orderServices.updateOrderDetails(response.getId(), orderUpdateRequest2);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(12);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(88);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void test_AllUpdateDetailIsNullOrEmpty_DetailDoesNotChangeInDB()
            throws BusinessLogicException {
    //given
    OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

    //when

        OrderUpdateRequest orderUpdateRequest3 = new OrderUpdateRequest();

        orderUpdateRequest3.setQuantity(0);
        orderUpdateRequest3.setSize(0);
        OrderCreationResponse response1 = orderServices.updateOrderDetails(response.getId(), orderUpdateRequest3);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(12);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(14);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void updateOrderStatus() throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        OrderCreationResponse response1 = orderServices.updateOrderStatus(response.getId(), OrderStatus.DELIVERED);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(response.getQuantity());
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(response.getSize());
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());


    }

    @Test
    public void updateOrderDeliverDate() throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        OrderCreationResponse response1 = orderServices.updateOrderDeliverDate(response.getId(),
                response.getOrderDate().plusDays(5).truncatedTo(ChronoUnit.DAYS));

        //assert
        assertThat(response1.getQuantity()).isEqualTo(response.getQuantity());
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getSize()).isEqualTo(response.getSize());
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getOrderDate().plusDays(5).truncatedTo(ChronoUnit.DAYS));

    }

    @Test
    public void deleteAllOrders() throws BusinessLogicException {
        //given
        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);

        orderServices.createOrder(orderCreationRequest1);
        orderServices.createOrder(orderCreationRequest);

        //when
        boolean isDeleted = orderServices.deleteAllOrders();

        //assert
        assertThat(isDeleted).isTrue();
        assertThatThrownBy(()->orderServices.getAllOrders())
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no orders here!");
    }

    @Test
    public void getOrderById() throws BusinessLogicException {
        //given
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest);

        //when
        OrderCreationResponse response1 = orderServices.getOrderById(response.getId());

        //assert
        assertThat(response.getId()).isEqualTo(response1.getId());
        assertThat(response.getOrderDate()).isEqualTo(response1.getOrderDate());
        assertThat(response.getDeliveryDate()).isEqualTo(response1.getDeliveryDate());
        assertThat(response.getQuantity()).isEqualTo(response1.getQuantity());
    }

    @Test
    public void getOrdersByDate() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        orderServices.deleteOrderByUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setOrderDate(LocalDateTime.now());
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");

        OrderCreationResponse response =orderServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =orderServices.createOrder(orderCreationRequest1);

        //when
        List<PrintOrder> orders = orderServices.getOrdersByDate(response.getOrderDate());

        //assert
        assertThat(response.getId()).isEqualTo(orders.get(0).getId());
        assertThat(response.getQuantity()).isEqualTo(orders.get(0).getQuantity());
        assertThat(response1.getId()).isEqualTo(orders.get(1).getId());
        assertThat(response1.getQuantity()).isEqualTo(orders.get(1).getQuantity());

    }

    @Test
    public void test_EmptyOrdersBy_Date_SendThereAreNoOrdersWithThisDate() throws OrderException {
        //assert
        orderServices.deleteAllOrders();
        assertThatThrownBy(()->orderServices.getOrdersByDate(LocalDateTime.now()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no orders with this date "
                        +LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)+"!");
    }

    @Test
    public void getOrdersByStatus() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setOrderDate(LocalDateTime.now());
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");

        OrderCreationResponse response =orderServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =orderServices.createOrder(orderCreationRequest1);

        OrderCreationResponse response2 =orderServices.updateOrderStatus(response.getId(), OrderStatus.PENDING);

        //when
        List<PrintOrder> ordersByStatus1 = orderServices.getOrdersByStatus(response1.getOrderStatus());
        List<PrintOrder> ordersByStatus2 = orderServices.getOrdersByStatus(response2.getOrderStatus());

        //assert
        assertThat(ordersByStatus1.size()).isEqualTo(1);
        assertThat(ordersByStatus1.get(0).getId()).isEqualTo(response1.getId());
        assertThat(ordersByStatus1.get(0).getOrderStatus()).isEqualTo(response1.getOrderStatus());

        assertThat(ordersByStatus2.get(0).getOrderStatus()).isEqualTo(response2.getOrderStatus());
        assertThat(ordersByStatus2.get(0).getId()).isEqualTo(response.getId());
        assertThat(response2.getId()).isEqualTo(response.getId());
        assertThat(ordersByStatus2.size()).isEqualTo(1);

    }

    @Test
    public void test_EmptyOrdersBy_Status_SendThereAreNoOrdersInThisStatus(){
        //assert
        assertThatThrownBy(()->orderServices.getOrdersByStatus(OrderStatus.DELIVERED))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no orders in this state "+OrderStatus.DELIVERED);
    }

    @Test
    public void getOrdersByUserId() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        orderServices.deleteOrderByUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setOrderDate(LocalDateTime.now());
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");
        orderServices.deleteAllOrders();
        OrderCreationResponse response =orderServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =orderServices.createOrder(orderCreationRequest1);

        //when
        List<PrintOrder> ordersByUserId = orderServices.getOrdersByUserId(userId);
        List<PrintOrder> ordersByUserId1 = orderServices.getOrdersByUserId("anId");

        //assert
        assertThat(ordersByUserId.size()).isEqualTo(1);
        assertThat(ordersByUserId.get(0).getId()).isEqualTo(response.getId());
        assertThat(ordersByUserId.get(0).getQuantity()).isEqualTo(response.getQuantity());
        assertThat(ordersByUserId.get(0).getSize()).isEqualTo(response.getSize());


        assertThat(ordersByUserId1.size()).isEqualTo(1);
        assertThat(ordersByUserId1.get(0).getId()).isEqualTo(response1.getId());
        assertThat(ordersByUserId1.get(0).getQuantity()).isEqualTo(response1.getQuantity());
        assertThat(ordersByUserId1.get(0).getSize()).isEqualTo(response1.getSize());
    }

    @Test
    public void test_InvalidUserId_ThrowsBusinessException(){
        //given
        String invalidId = "anInvalidId";

        //assert
        assertThatThrownBy(()->orderServices.getOrdersByUserId(invalidId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("This user, anInvalidId, does not have any orders!");

    }

    @Test
    public void getAllOrders() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        orderServices.deleteOrderByUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setOrderDate(LocalDateTime.now());
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");
        orderServices.deleteAllOrders();


        OrderCreationRequest orderCreationRequest2 = new OrderCreationRequest();
        orderCreationRequest2.setOrderDate(LocalDateTime.now());
        orderCreationRequest2.setSize(10);
        orderCreationRequest2.setQuantity(100);
        orderCreationRequest2.setImageUrl("myimageurleurl.com");
        orderCreationRequest2.setUserId("anotherId");
        OrderCreationResponse response =orderServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =orderServices.createOrder(orderCreationRequest1);
        OrderCreationResponse response2 =orderServices.createOrder(orderCreationRequest2);

        //when
        List<PrintOrder> ordersList = orderServices.getAllOrders();

        //assert
        assertThat(ordersList.size()).isEqualTo(3);
        assertThat(response.getQuantity()).isEqualTo(ordersList.get(0).getQuantity());
        assertThat(response.getId()).isEqualTo(ordersList.get(0).getId());
        assertThat(response.getId()).isEqualTo(ordersList.get(0).getId());
        assertThat(response1.getQuantity()).isEqualTo(ordersList.get(1).getQuantity());
        assertThat(response1.getId()).isEqualTo(ordersList.get(1).getId());
        assertThat(response1.getId()).isEqualTo(ordersList.get(1).getId());
        assertThat(response2.getQuantity()).isEqualTo(ordersList.get(2).getQuantity());
        assertThat(response2.getId()).isEqualTo(ordersList.get(2).getId());
        assertThat(response2.getId()).isEqualTo(ordersList.get(2).getId());
    }
}