package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogic;
import ozi.app.printer.exceptions.OrderExceptions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringBootTest
@Slf4j
class OrderServicesImplTest {
    @Autowired
    private OrderServices orderServices;

    private OrderCreationRequest request;
    @BeforeEach
    void setUp() {
        request= new OrderCreationRequest();
        request.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        request.setSize(12.5);
        request.setImageUrl("imageUrl");
        request.setQuantity(1);
    }

    @AfterEach
    void tearDown() throws OrderExceptions {
//        orderServices.clearAllOrders();
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
        assertThat(foundOrder.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(foundOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(foundOrder.getSize()).isEqualTo(response.getSize());
        assertThat(foundOrder.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
        assertThat(foundOrder.getQuantity()).isEqualTo(response.getQuantity());


    }

    @Test
    public void test_ThrowOrderDoesNotExistException_WhenUnknownId_FindById(){
        //assert
        assertThatThrownBy(()->orderServices.getOrderById("an_invalid_id"))
                .isInstanceOf(OrderExceptions.class)
                .hasMessage("This user with id an_invalid_id does not exist");
    }

    @Test
    public void testThrowNoOrdersError_WhenClearAll_OnEmptyDB(){
        //assert
        assertThatThrownBy(()->orderServices
                .getAllOrders()).isInstanceOf(OrderExceptions.class)
                .hasMessage("There are no orders here!");
    }

    @Test
    public void deleteOrderById() throws BusinessLogic {
        //given
        OrderCreationResponse response = orderServices.createOrder(request);
        //when
        boolean orderIsDeleted = orderServices.deleteOrderById(response.getId());

        //assert
        assertThatThrownBy(()->orderServices
                .getOrderById(response.getId()))
                .isInstanceOf(OrderExceptions.class)
                .hasMessage("This user with id " +response.getId()+" does not exist");
        assertThat(orderIsDeleted).isTrue();

    }

    @Test
    public void updateOrder() throws BusinessLogic {
        //given
        OrderCreationResponse response = orderServices.createOrder(request);
        //when
        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setQuantity(100);
        OrderCreationResponse response1 = orderServices.updateOrder(response.getId(), request1);
        //assert
//        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
//        assertThat(response1.getSize()).isEqualTo(response.getSize());
//        assertThat(response1.getQuantity()).isEqualTo(orderServices.getOrderById(response.getId()).getQuantity());


    }

    @Test
    public void getAllOrders() throws BusinessLogic {
        //given
        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setOrderDate(LocalDateTime.now());
        request1.setSize(12.5);
        request1.setImageUrl("imageUrl");
        request1.setQuantity(1);
        orderServices.createOrder(request);
        orderServices.createOrder(request1);

        //when
        List<PrintOrder> orders = orderServices.getAllOrders();

        assertThat(orders.size()).isEqualTo(2);

    }


    @Test
    public void getOrdersByDate() throws BusinessLogic {
        //given
        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setOrderDate(LocalDateTime.now());
        request1.setSize(12.5);
        request1.setImageUrl("imageUrl");
        request1.setQuantity(1);
        OrderCreationResponse response = orderServices.createOrder(request);
        OrderCreationResponse response1 = orderServices.createOrder(request1);
        //when
        List<PrintOrder> orders = orderServices.getOrdersByDate(response.getOrderDate());
        //assert
        assertThat(orders.get(0).getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(orders.get(1).getOrderDate()).isEqualTo(response1.getOrderDate());
    }

    @Test
    public void getOrdersByStatus() throws BusinessLogic {
        //given
        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setOrderDate(LocalDateTime.now());
        request1.setSize(12.5);
        request1.setImageUrl("imageUrl");
        request1.setQuantity(1);
        OrderCreationResponse response = orderServices.createOrder(request);
        OrderCreationResponse response1 = orderServices.createOrder(request1);
        //when
        List<PrintOrder> orders = orderServices.getOrdersByStatus(response.getOrderStatus());
        log.info(orderServices.getAllOrders().toString());
        //assert
        assertThat(orders.get(0).getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(orders.get(1).getOrderStatus()).isEqualTo(response1.getOrderStatus());
        assertThat(orderServices.getAllOrders().size()).isEqualTo(2);
    }
}