package ozi.app.printer.services.orderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.OrderUpdateRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.repositories.OrderRepository;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;
import ozi.app.printer.mapper.Mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderCreationResponse createOrder(OrderCreationRequest request)
            throws BusinessLogicException {
        validate(request);
        PrintOrder order = Mapper.map(request);
        setOtherDetailsFor(order);
        PrintOrder savedOrder = save(order);
        return Mapper.map(savedOrder);
    }

    private void validate(OrderCreationRequest request) throws BusinessLogicException {
        boolean imageUrlIsEmpty= request.getImageUrl() == null;
        boolean sizeIsEmpty = request.getSize()==0;
        boolean quantityIsEmpty = request.getQuantity() == 0;
        boolean userIdIsEmpty = request.getUserId()==null;

        if ( imageUrlIsEmpty|| sizeIsEmpty|| quantityIsEmpty || userIdIsEmpty ){
            throw new OrderException("The given details are incomplete");
        }
    }

    private void setOtherDetailsFor(PrintOrder order) {
        double price = order.getQuantity() * order.getSize();
        order.setPrice(price);
        order.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        order.setOrdered(true);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setDeliveryDate(order.getOrderDate().plusDays(3) );
    }

    private PrintOrder save(PrintOrder order) {

        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrderById(String id) throws BusinessLogicException {
        Optional<PrintOrder> optionalPrintOrder = orderRepository.findById(id);
        if ( optionalPrintOrder.isEmpty() )throw new BusinessLogicException("No such order exists!");
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteOrderByUserId(String userId){
        List<PrintOrder> orders = orderRepository.findPrintOrderByUserId(userId);
        if ( orders.isEmpty())return false;
        orderRepository.deleteAll(orders);
        return true;
    }

    @Override
    public OrderCreationResponse updateOrderDetails(String orderId, OrderUpdateRequest request)
            throws BusinessLogicException {
        Optional<PrintOrder> optionalPrintOrder = orderRepository.findById(orderId);
        if ( optionalPrintOrder.isEmpty() ) throw new BusinessLogicException("There is no order with ID "+orderId+" here!");
        PrintOrder order = optionalPrintOrder.get();
        if ( request.getSize() > 0 ) order.setSize(request.getSize());

        if ( request.getQuantity() >0 )order.setQuantity(request.getQuantity());

        orderRepository.save(order);
        return Mapper.map(order);
    }

    @Override
    public OrderCreationResponse updateOrderStatus(String orderId, OrderStatus status)
            throws BusinessLogicException {
        Optional<PrintOrder> optionalPrintOrder = orderRepository.findById(orderId);
        if ( optionalPrintOrder.isEmpty() ) throw new BusinessLogicException("There is no order with ID "+orderId+" here!");
        PrintOrder order = optionalPrintOrder.get();
        order.setOrderStatus(status);

        orderRepository.save(order);
        return Mapper.map(order);
    }

    @Override
    public OrderCreationResponse updateOrderDeliverDate(String orderId, LocalDateTime date)
            throws BusinessLogicException {
        Optional<PrintOrder> optionalPrintOrder = orderRepository.findById(orderId);
        if ( optionalPrintOrder.isEmpty() ) throw new BusinessLogicException("There is no order with ID "+orderId+" here!");
        PrintOrder order = optionalPrintOrder.get();
        order.setDeliveryDate(date.truncatedTo(ChronoUnit.DAYS));

        orderRepository.save(order);
        return Mapper.map(order);
    }

    @Override
    public boolean deleteAllOrders() throws OrderException {
        if (orderRepository.findAll().size()==0 )
            throw new OrderException("There are no orders here!");

        orderRepository.deleteAll();
        return true;
    }

    @Override
    public OrderCreationResponse getOrderById(String id) throws OrderException {
        Optional<PrintOrder> optionalPrintOrder = orderRepository.findById(id);
        if ( optionalPrintOrder.isEmpty() ){
            throw new OrderException("This user with id " +id+" does not exist");
        }
        return Mapper.map(optionalPrintOrder.get());
    }

    @Override
    public List<PrintOrder> getOrdersByDate(LocalDateTime date) throws BusinessLogicException {
        LocalDateTime truncatedDate = date.truncatedTo(ChronoUnit.DAYS);
        List<PrintOrder> ordersByDate = orderRepository.findByOrderDate(truncatedDate);
        if ( ordersByDate.isEmpty() ) throw new OrderException("There are no orders with this date "+truncatedDate+"!");
        return ordersByDate;
    }

    @Override
    public List<PrintOrder> getOrdersByStatus(OrderStatus status) throws BusinessLogicException {
        List<PrintOrder> ordersByStatus = orderRepository.findByOrderStatus(status);
        if ( ordersByStatus.isEmpty() ) throw new OrderException("There are no orders in this state "+status);
        return ordersByStatus;
    }

    @Override
    public List<PrintOrder> getOrdersByUserId(String userId) throws OrderException {
        List<PrintOrder> ordersByUserId = orderRepository.findPrintOrderByUserId(userId);
        if ( ordersByUserId.isEmpty() )
            throw new OrderException("This user, "+userId+", does not have any orders!");
        return ordersByUserId;
    }

    @Override
    public List<PrintOrder> getAllOrders() throws OrderException {
        if ( orderRepository.findAll().size() == 0 )throw new OrderException("There are no orders here!");
        return orderRepository.findAll();
    }

}