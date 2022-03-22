package ozi.app.printer.services.userService;

import com.fasterxml.jackson.core.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.repositories.UserRepository;
import ozi.app.printer.services.userService.UserServices;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserCreationResponse createUser(UserCreationRequest request) {

        UserCreationResponse response = new UserCreationResponse();
        response.setFirstName(request.getFirstName());
        response.setUsername(response.getUsername());
        response.setLastName(request.getLastName());
        response.setEmail(request.getEmail());
        return response;
    }

    @Override
    public UserCreationResponse getUserById(String id) {
        return null;
    }

    @Override
    public UserCreationResponse getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserCreationResponse getUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public UserCreationResponse getUserByNamesAndPassword(String firstName, String lastName, String password) {
        return null;
    }

    @Override
    public List<PrintUser> getAllUsers() {
        return null;
    }

    @Override
    public UserCreationResponse updateUser(UserCreationRequest request) {
        return null;
    }

    @Override
    public boolean deleteUserById(String id) {
        return false;
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        return false;
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        return false;
    }

    @Override
    public boolean deleteAllUsers() {
        return false;
    }
}
