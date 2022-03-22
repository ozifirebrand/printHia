package ozi.app.printer.services.userService;

import com.fasterxml.jackson.core.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.repositories.UserRepository;
import ozi.app.printer.exceptions.BusinessLogic;
import ozi.app.printer.services.userService.UserServices;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogic {
        if ( request.getEmail() == null || request.getUsername() == null|| request.getFirstName() == null|| request.getLastName() == null || request.getPassword() == null ){
            throw new BusinessLogic("Incomplete details");
        }

        UserCreationResponse response = new UserCreationResponse();
        response.setFirstName(request.getFirstName());
        response.setUsername(response.getUsername());
        response.setLastName(request.getLastName());
        response.setEmail(request.getEmail());
        return response;
    }

    @Override
    public Optional<PrintUser> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<PrintUser> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<PrintUser> getUserByUsernameAndPassword(String username, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<PrintUser> getUserByNamesAndPassword(String firstName, String lastName, String password) {
        return Optional.empty();
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