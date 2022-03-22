package ozi.app.printer.services.userService;

import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.exceptions.BusinessLogic;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogic;
    Optional<PrintUser> getUserById(String id);
    Optional<PrintUser> getUserByEmail(String email);
    Optional<PrintUser> getUserByUsernameAndPassword(String username, String password);
    Optional<PrintUser> getUserByNamesAndPassword(String firstName, String lastName, String password);
    List<PrintUser> getAllUsers();
    UserCreationResponse updateUser(UserCreationRequest request);
    boolean deleteUserById(String id);
    boolean deleteUserByEmail(String email);
    boolean deleteUserByUsername(String username);
    boolean deleteAllUsers();
}