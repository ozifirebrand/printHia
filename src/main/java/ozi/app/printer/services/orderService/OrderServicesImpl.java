package ozi.app.printer.services.orderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderCreationResponse createOrder(OrderCreationRequest request) {
        PrintOrder order = new PrintOrder();
        order.setOrderDate(request.getOrderDate());
        order.setImageUrl(request.getImageUrl());
        order.setSize(request.getSize());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setQuantity(request.getQuantity());
        PrintOrder savedOrder = orderRepository.save(order);
        savedOrder.setOrdered(true);
        savedOrder.setOrderStatus(OrderStatus.ORDERED);
        savedOrder.setDeliveryDate(savedOrder.getOrderDate().plusDays(3) );

        OrderCreationResponse response = new OrderCreationResponse();
        response.setOrderDate(savedOrder.getOrderDate());
        response.setOrderStatus(savedOrder.getOrderStatus());
        response.setQuantity(savedOrder.getQuantity());
        response.setSize(savedOrder.getSize());
        response.setDeliveryDate(savedOrder.getDeliveryDate());
        response.setImageUrl(savedOrder.getImageUrl());

        return response;
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