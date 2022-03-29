package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
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
@Slf4j
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
        setDateFor(request);
        boolean orderDateIsEmpty= request.getOrderDate() == null;
        boolean imageUrlIsEmpty= request.getImageUrl() == null;
        boolean sizeIsEmpty = request.getSize()==0;
        boolean quantityIsEmpty = request.getQuantity() == 0;
        boolean userIdIsEmpty = request.getUserId()==null;

        if ( orderDateIsEmpty || imageUrlIsEmpty|| sizeIsEmpty|| quantityIsEmpty || userIdIsEmpty ){
            throw new OrderException("The given details are incomplete");
        }
    }

    private void setOtherDetailsFor(PrintOrder order) {
        order.setOrdered(true);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setDeliveryDate(order.getOrderDate().plusDays(3) );
    }

    private void setDateFor(OrderCreationRequest request) {
        request.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }

    private List<PrintOrder> getOrdersWith(String userId) {

        return orderRepository.findPrintOrderByUserId(userId);
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
    public void updateOrderDetails(String orderId, OrderCreationRequest request) {

    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus status) {
    }

    @Override
    public void updateOrderDeliverDate(String orderId, LocalDateTime date) {
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
    public List<PrintOrder> getOrdersByDate(LocalDateTime date) {
        LocalDateTime truncatedDate = date.truncatedTo(ChronoUnit.DAYS);
        return orderRepository.findByOrderDate(truncatedDate);
    }

    @Override
    public List<PrintOrder> getOrdersByStatus(OrderStatus status) {

        return orderRepository.findByOrderStatus(status);
    }

    @Override
    public List<PrintOrder> getOrdersByUserId(String userId) {
        return orderRepository.findPrintOrderByUserId(userId);
    }

    @Override
    public List<PrintOrder> getAllOrders() throws OrderException {
        if ( orderRepository.findAll().size() == 0 )throw new OrderException("There are no orders here!");
        return orderRepository.findAll();
    }

}