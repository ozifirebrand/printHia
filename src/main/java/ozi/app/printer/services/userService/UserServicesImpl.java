package ozi.app.printer.services.userService;

import com.fasterxml.jackson.core.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.repositories.UserRepository;
import ozi.app.printer.exceptions.BusinessLogic;
import ozi.app.printer.mapper.Mapper;
import ozi.app.printer.services.userService.UserServices;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogic {
        validateRequestDetails(request);

        PrintUser user = Mapper.map(request);
        PrintUser savedUser = userRepository.save(user);
        return Mapper.map(savedUser);
    }

    private void validateRequestDetails(UserCreationRequest request) throws BusinessLogic {
        boolean emailIsEmpty= request.getEmail() == null;
        boolean usernameIsEmpty= request.getUsername() == null;
        boolean firstNameIsEmpty = request.getFirstName() == null;
        boolean lastNameIsEmpty = request.getLastName() == null;
        boolean passwordIsEmpty = request.getPassword() == null;

        if ( emailIsEmpty || usernameIsEmpty|| firstNameIsEmpty|| lastNameIsEmpty|| passwordIsEmpty ){
            throw new BusinessLogic("Incomplete details");
        }
    }

    @Override
    public PrintUser getUserById(String id) throws BusinessLogic {
        Optional<PrintUser> optionalPrintUser = userRepository.findById(id);
        if ( optionalPrintUser.isEmpty() ){
            throw new BusinessLogic("This user does not exist!");
        }
        return optionalPrintUser.get();
    }

    @Override
    public PrintUser getUserByEmail(String email) {
        return null;
    }

    @Override
    public PrintUser getUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public PrintUser getUserByNamesAndPassword(String firstName, String lastName, String password) {
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
