package ozi.app.printer.services.userService;

import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;

import java.util.List;

public interface UserServices {
    UserCreationResponse createUser(UserCreationRequest request);
    UserCreationResponse getUserById(String id);
    UserCreationResponse getUserByEmail(String email);
    UserCreationResponse getUserByUsernameAndPassword(String username, String password);
    UserCreationResponse getUserByNamesAndPassword(String firstName, String lastName, String password);
    List<PrintUser> getAllUsers();
    UserCreationResponse updateUser(UserCreationRequest request);
    boolean deleteUserById(String id);
    boolean deleteUserByEmail(String email);
    boolean deleteUserByUsername(String username);
    boolean deleteAllUsers();
}
