package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.repositories.OrderRepository;
import ozi.app.printer.exceptions.BusinessLogic;
import ozi.app.printer.exceptions.OrderExceptions;
import ozi.app.printer.mapper.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderCreationResponse createOrder(OrderCreationRequest request) throws BusinessLogic {
        validate(request);
        PrintOrder order = Mapper.map(request);
        order.setOrdered(true);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setDeliveryDate(order.getOrderDate().plusDays(3) );

        PrintOrder savedOrder = saveOrder(order);
        return Mapper.map(savedOrder);
    }

    private PrintOrder saveOrder( PrintOrder order) {

        return orderRepository.save(order);
    }

    private void validate(OrderCreationRequest request) throws BusinessLogic {
        boolean orderIsEmpty= request.getOrderDate() == null;
        boolean imageUrlIsEmpty= request.getImageUrl() == null;
        boolean sizeIsEmpty = request.getSize()==0;
        boolean quantityIsEmpty = request.getQuantity() == 0;

        if ( orderIsEmpty || imageUrlIsEmpty|| sizeIsEmpty|| quantityIsEmpty ){
            throw new BusinessLogic("The given details are incomplete");
        }
    }

    @Override
    public PrintOrder getOrderById(String id) throws OrderExceptions {
        Optional<PrintOrder> optionalPrintOrder = orderRepository.findById(id);
        if ( optionalPrintOrder.isEmpty() ){
            throw new OrderExceptions("This user with id " +id+" does not exist");
        }
        return optionalPrintOrder.get();
    }

    @Override
    public boolean clearAllOrders() throws OrderExceptions {
//        if (orderRepository.findAll().size()==0 )
//            throw new OrderExceptions("There are no orders here!");

        orderRepository.deleteAll();
        return getAllOrders().size()==0;
    }

    @Override
    public boolean deleteOrderById(String id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public OrderCreationResponse updateOrder(String id, OrderCreationRequest request) throws OrderExceptions {
        PrintOrder order = getOrderById(id);
        if ( order!= null )
        saveOrder(order);
        return null;
    }

    @Override
    public List<PrintOrder> getAllOrders() throws OrderExceptions {
        if ( orderRepository.findAll().size() == 0 )throw new OrderExceptions("There are no orders here!");
        return orderRepository.findAll();
    }

    @Override
    public List<PrintOrder> getOrdersByDate(LocalDateTime dateTime) {
        return orderRepository.findByOrderDate(dateTime);
    }

    @Override
    public List<PrintOrder> getOrdersByStatus(OrderStatus status) {
        return null;
    }
}