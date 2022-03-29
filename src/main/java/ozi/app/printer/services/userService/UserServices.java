package ozi.app.printer.services.userService;

import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.models.Role;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.util.List;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request)
            throws BusinessLogicException;
    boolean deleteUserById(String userId);
    boolean deleteAllUsers() throws BusinessLogicException;
    void updateUserRole(String userId, Role role);
    List<PrintUser> getAllUsers();
    PrintUser getUserById(String userId) throws BusinessLogicException;
    PrintUser getUserByEmail(String email) throws BusinessLogicException;
}
