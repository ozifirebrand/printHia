package ozi.app.printer.services.userService;

import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.util.List;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogicException;
    PrintUser getUserById(String id) throws BusinessLogicException;
    PrintUser getUserByEmail(String email) throws BusinessLogicException;
    List<PrintUser> getAllUsers();
    UserCreationResponse updateUser(UserCreationRequest request);
    boolean deleteUserById(String id);
    boolean deleteAllUsers() throws BusinessLogicException;
}
