package ozi.app.printer.services.orderService;

import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServicesImpl implements OrderServices {
    @Override
    public OrderCreationResponse createOrder(OrderCreationRequest request) {
        return null;
    }

    @Override
    public OrderCreationResponse getOrderById(String id) {
        return null;
    }

    @Override
    public boolean clearAllOrders() {
        return false;
    }

    @Override
    public boolean deleteOrderById(String id) {
        return false;
    }

    @Override
    public List<PrintOrder> getAllOrders() {
        return null;
    }

    @Override
    public List<PrintOrder> getOrdersByUsername(String username) {
        return null;
    }

    @Override
    public List<PrintOrder> getOrdersByDate(LocalDateTime dateTime) {
        return null;
    }

    @Override
    public List<PrintOrder> getOrdersByStatus(OrderStatus status) {
        return null;
    }
}