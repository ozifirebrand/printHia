package ozi.app.printer.services.orderService;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.repositories.UserRepository;

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
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        return null;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime dateTime) {
        return null;
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return null;
    }
}