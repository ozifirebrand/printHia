package ozi.app.printer.services.orderService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogic;
import ozi.app.printer.exceptions.OrderExceptions;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringBootTest
class OrderServicesImplTest {
    @Autowired
    private OrderServices orderServices;

    private OrderCreationRequest request;
    @BeforeEach
    void setUp() {
        request= new OrderCreationRequest();
        request.setOrderDate(LocalDateTime.now());
        request.setSize(12.5);
        request.setImageUrl("imageUrl");
        request.setQuantity(1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder() throws BusinessLogic {
        //given

        //when
        OrderCreationResponse response =orderServices.createOrder(request);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getOrderDate()).isEqualTo(request.getOrderDate());
        assertThat(response.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(response.getSize()).isEqualTo(request.getSize());
        assertThat(response.getImageUrl()).isEqualTo(request.getImageUrl());
        assertThat(response.isOrdered()).isTrue();
    }

    @Test
    public void testIncompleteDetails_AbsentSizeAttribute_ThrowIncompleteDetailsException(){
        //given
        OrderCreationRequest request = new OrderCreationRequest();
        request.setOrderDate(LocalDateTime.now());
        request.setImageUrl("imageUrl2");
        request.setQuantity(1);
        //when
        //assert

        assertThatThrownBy(()->orderServices.createOrder(request)).isInstanceOf(BusinessLogic.class).hasMessage("The given details are incomplete");

    }

    @Test
    public void getOrderById() throws BusinessLogic {
        //given...
        OrderCreationResponse response =orderServices.createOrder(request);

        //when
        PrintOrder foundOrder = orderServices.getOrderById(response.getId());
        //assert
        assertThat(foundOrder.getImageUrl()).isEqualTo(response.getImageUrl());
//        assertThat(foundOrder.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(foundOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(foundOrder.getSize()).isEqualTo(response.getSize());
        assertThat(foundOrder.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
        assertThat(foundOrder.getQuantity()).isEqualTo(response.getQuantity());


    }

    @Test
    public void test_ThrowUserDoesNotExistException_WhenUnknownId_FindById(){
        //assert
        assertThatThrownBy(()->orderServices.getOrderById("an_invalid_id"))
                .isInstanceOf(OrderExceptions.class)
                .hasMessage("This user with id an_invalid_id does not exist");
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