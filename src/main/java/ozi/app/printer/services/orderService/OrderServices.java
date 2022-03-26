package ozi.app.printer.services.orderService;

import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderServices {

    OrderCreationResponse createOrder(OrderCreationRequest request) throws BusinessLogicException;
    OrderCreationResponse getOrderById(String id) throws OrderException;
    boolean clearAllOrders() throws OrderException;
    boolean deleteOrderById(String id);
    List<PrintOrder> getAllOrders() throws OrderException;
    List<PrintOrder> getOrdersByDate(LocalDateTime dateTime);
    List<PrintOrder> getOrdersByStatus(OrderStatus status);
}
