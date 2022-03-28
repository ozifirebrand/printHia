package ozi.app.printer.services.orderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.repositories.OrderRepository;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.exceptions.OrderException;
import ozi.app.printer.mapper.Mapper;
import ozi.app.printer.services.userService.UserServices;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserServices userServices;

    @Override
    public OrderCreationResponse createOrder(OrderCreationRequest request, String userId)
            throws BusinessLogicException {

        List<PrintOrder> orders = getOrdersWith(userId);

        setDateFor(request);
        validate(request);
        PrintOrder order = Mapper.map(request);
        setOtherDetailsFor(order);

        PrintOrder savedOrder = save(order);
        orders.add(order);
        return Mapper.map(savedOrder);
    }

    private void setOtherDetailsFor(PrintOrder order) {
        order.setOrdered(true);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setDeliveryDate(order.getOrderDate().plusDays(3) );
    }

    private void setDateFor(OrderCreationRequest request) {
        request.setOrderDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }

    private List<PrintOrder> getOrdersWith(String userId) throws BusinessLogicException {
        PrintUser printUser = userServices.getUserById(userId);
        return printUser.getOrders();
    }

    private PrintOrder save(PrintOrder order) {

        return orderRepository.save(order);
    }

    private void validate(OrderCreationRequest request) throws BusinessLogicException {
        boolean orderDateIsEmpty= request.getOrderDate() == null;
        boolean imageUrlIsEmpty= request.getImageUrl() == null;
        boolean sizeIsEmpty = request.getSize()==0;
        boolean quantityIsEmpty = request.getQuantity() == 0;

        if ( orderDateIsEmpty || imageUrlIsEmpty|| sizeIsEmpty|| quantityIsEmpty ){
            throw new OrderException("The given details are incomplete");
        }
    }

    @Override
    public boolean deleteOrderById(String id) {
        orderRepository.deleteById(id);
        return true;
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
        return orderRepository.getByOrderDate(truncatedDate);
    }

    @Override
    public List<PrintOrder> getOrdersByStatus(OrderStatus status) {

        return orderRepository.findByOrderStatus(status);
    }

    @Override
    public List<PrintOrder> getOrdersByUserId(String userId) {
        return null;
    }

    @Override
    public List<PrintOrder> getAllOrders() throws OrderException {
        if ( orderRepository.findAll().size() == 0 )throw new OrderException("There are no orders here!");
        return orderRepository.findAll();
    }

}