package ozi.app.printer.services.orderService;

import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderServices {

    OrderCreationResponse createOrder(OrderCreationRequest request) throws BusinessLogic;
    PrintOrder getOrderById(String id);
    boolean clearAllOrders();
    boolean deleteOrderById(String id);
    List<PrintOrder> getAllOrders();
    List<PrintOrder> getOrdersByUsername(String username);
    List<PrintOrder> getOrdersByDate(LocalDateTime dateTime);
    List<PrintOrder> getOrdersByStatus(OrderStatus status);
}
