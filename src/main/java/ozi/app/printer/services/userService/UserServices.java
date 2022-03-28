package ozi.app.printer.services.userService;

import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.util.Collection;
import java.util.List;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogicException;
    PrintUser getUserById(String id) throws BusinessLogicException;
    PrintUser getUserByEmail(String email) throws BusinessLogicException;
    List<PrintUser> getAllUsers();
    boolean deleteUserById(String id);
    void deleteAllUsers() throws BusinessLogicException;
    OrderCreationResponse makeOrder(OrderCreationRequest request, String id);
    List<PrintOrder> getAllOrders(String userId);
}
