package ozi.app.printer.services.userService;

import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.exceptions.BusinessLogic;

import java.util.List;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogic;
    PrintUser getUserById(String id) throws BusinessLogic;
    PrintUser getUserByEmail(String email) throws BusinessLogic;
    List<PrintUser> getAllUsers();
    UserCreationResponse updateUser(UserCreationRequest request);
    boolean deleteUserById(String id);
    boolean deleteAllUsers() throws BusinessLogic;
}
