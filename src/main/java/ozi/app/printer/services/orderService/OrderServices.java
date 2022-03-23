package ozi.app.printer.services.orderService;

import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogic;
import ozi.app.printer.exceptions.OrderExceptions;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderServices {

    OrderCreationResponse createOrder(OrderCreationRequest request) throws BusinessLogic;
    PrintOrder getOrderById(String id) throws OrderExceptions;
    boolean clearAllOrders() throws OrderExceptions;
    boolean deleteOrderById(String id);
    OrderCreationResponse updateOrder(String id, OrderCreationRequest request) throws OrderExceptions;
    List<PrintOrder> getAllOrders() throws OrderExceptions;
    List<PrintOrder> getOrdersByDate(LocalDateTime dateTime);
    List<PrintOrder> getOrdersByStatus(OrderStatus status);
}
