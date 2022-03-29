package ozi.app.printer.services.orderService;

import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.OrderUpdateRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderServices {

    OrderCreationResponse createOrder(OrderCreationRequest request) throws BusinessLogicException;
    boolean deleteAllOrders() throws OrderException;
    boolean deleteOrderById(String id) throws BusinessLogicException;
    boolean deleteOrderByUserId(String userId) ;
    OrderCreationResponse updateOrderDetails(String orderId, OrderUpdateRequest request) throws BusinessLogicException;
    OrderCreationResponse updateOrderStatus(String orderId, OrderStatus status);
    OrderCreationResponse updateOrderDeliverDate(String orderId, LocalDateTime date);
    OrderCreationResponse getOrderById(String id) throws OrderException;
    List<PrintOrder> getAllOrders() throws OrderException;
    List<PrintOrder> getOrdersByDate(LocalDateTime dateTime);
    List<PrintOrder> getOrdersByStatus(OrderStatus status);
    List<PrintOrder> getOrdersByUserId(String userId);
}
