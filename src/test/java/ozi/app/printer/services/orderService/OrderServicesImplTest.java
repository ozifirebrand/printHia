package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;
import ozi.app.printer.services.userService.UserServices;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringBootTest
@Slf4j
class OrderServicesImplTest {
    @Autowired
    @Mock
    private OrderServices orderServices;


    @Autowired
    @Mock
    private UserServices userServices;

    private OrderCreationRequest orderCreationRequest;
    private UserCreationResponse userCreationResponse;

    @BeforeEach
    void setUp() throws BusinessLogicException {
        orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        orderCreationRequest.setSize(12.5);
        orderCreationRequest.setImageUrl("imageUrl");
        orderCreationRequest.setQuantity(1);

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setLastName("Mobola");
        userCreationRequest.setUsername("herUsername");
        userCreationRequest.setPassword("amiMobOla?");
        userCreationRequest.setEmail("mobola@gmail.com");
        userCreationRequest.setFirstName("Folade");
        userCreationResponse = userServices.createUser(userCreationRequest);

    }

    @AfterEach
    void tearDown() throws OrderException {
//        orderServices.deleteAllOrders();
    }

    @Test
    public void createOrder() throws BusinessLogicException {
        //given...

        //when
        String userId = userCreationResponse.getId();
        OrderCreationResponse response
                =orderServices.createOrder(orderCreationRequest, userId);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getOrderDate()).isEqualTo(orderCreationRequest.getOrderDate());
        assertThat(response.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(response.getSize()).isEqualTo(orderCreationRequest.getSize());
        assertThat(response.getImageUrl()).isEqualTo(orderCreationRequest.getImageUrl());
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

        assertThatThrownBy(()->orderServices.createOrder(request, userCreationResponse.getId()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("The given details are incomplete");

    }

    @Test
    public void getOrderById() throws BusinessLogicException {
        //given...
        String userId = userCreationResponse.getId();

        OrderCreationResponse response =orderServices.createOrder(orderCreationRequest, userId);

        //when
        OrderCreationResponse response1 = orderServices.getOrderById(response.getId());
        //assert
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
        assertThat(response1.getSize()).isEqualTo(response.getSize());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
        assertThat(response1.getQuantity()).isEqualTo(response.getQuantity());


    }

    @Test
    public void test_ThrowOrderDoesNotExistException_WhenUnknownId_FindById(){
        //assert
        assertThatThrownBy(()->orderServices.getOrderById("an_invalid_id"))
                .isInstanceOf(OrderException.class)
                .hasMessage("This user with id an_invalid_id does not exist");
    }

    @Test
    public void testThrowNoOrdersError_WhenClearAll_OnEmptyDB(){
        //assert
        assertThatThrownBy(()->orderServices
                .getAllOrders()).isInstanceOf(OrderException.class)
                .hasMessage("There are no orders here!");
    }

    @Test
    public void deleteOrderById() throws BusinessLogicException {
        //given
        String userId = userCreationResponse.getId();

        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest, userId);
        //when
        boolean orderIsDeleted = orderServices.deleteOrderById(response.getId());

        //assert
        assertThatThrownBy(()->orderServices
                .getOrderById(response.getId()))
                .isInstanceOf(OrderException.class)
                .hasMessage("This user with id " +response.getId()+" does not exist");
        assertThat(orderIsDeleted).isTrue();

    }

    @Test
    public void clearAllOrders() throws BusinessLogicException {
        //given
        String userId = userCreationResponse.getId();

        orderServices.createOrder(orderCreationRequest, userId);
        //when

        boolean isCleared = orderServices.deleteAllOrders();

        //assert
        assertThat(isCleared).isTrue();
        assertThatThrownBy(()->orderServices.getAllOrders())
                .isInstanceOf(BusinessLogicException.class).hasMessage("There are no orders here!");
    }

    @Test
    public void getAllOrders() throws BusinessLogicException {
        //given
        String userId = userCreationResponse.getId();

        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setOrderDate(LocalDateTime.now());
        request1.setSize(12.5);
        request1.setImageUrl("imageUrl");
        request1.setQuantity(1);
        orderServices.createOrder(orderCreationRequest, userId);
        orderServices.createOrder(request1, userId);

        //when
        List<PrintOrder> orders = orderServices.getAllOrders();

        assertThat(orders.size()).isEqualTo(2);

    }

    @Test
    public void getOrdersByDate() throws BusinessLogicException {
        //given
        String userId = userCreationResponse.getId();

        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setOrderDate(LocalDateTime.now());
        request1.setSize(12.5);
        request1.setImageUrl("imageUrl");
        request1.setQuantity(1);
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest, userId);
        OrderCreationResponse response1 = orderServices.createOrder(request1, userId);
        //when
        List<PrintOrder> orders = orderServices.getOrdersByDate(response.getOrderDate());
        //assert
        assertThat(orders.get(0).getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(orders.get(1).getOrderDate()).isEqualTo(response1.getOrderDate());
    }

    @Test
    public void getOrdersByStatus() throws BusinessLogicException {
        //given
        String userId = userCreationResponse.getId();

        OrderCreationRequest request1 = new OrderCreationRequest();
        request1.setOrderDate(LocalDateTime.now());
        request1.setSize(12.5);
        request1.setImageUrl("imageUrl");
        request1.setQuantity(1);
        OrderCreationResponse response = orderServices.createOrder(orderCreationRequest, userId);
        OrderCreationResponse response1 = orderServices.createOrder(request1, userId);
        //when
        List<PrintOrder> orders = orderServices.getOrdersByStatus(response.getOrderStatus());
        log.info(orderServices.getAllOrders().toString());
        //assert
        assertThat(orders.get(0).getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(orders.get(1).getOrderStatus()).isEqualTo(response1.getOrderStatus());
        assertThat(orderServices.getAllOrders().size()).isEqualTo(2);
    }
}