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
import java.util.Objects;
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
    public PrintUser getUserByEmail(String email) throws BusinessLogic {
        Optional<PrintUser> optionalPrintUser = userRepository.findPrintUserByEmail(email);
        if ( optionalPrintUser.isEmpty() ){
            throw new BusinessLogic("This user with email \""+email +"\" does not exist!");
        }
        return optionalPrintUser.get();
    }


    @Override
    public List<PrintUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserCreationResponse updateUser(UserCreationRequest request) {
        return null;
    }

    @Override
    public boolean deleteUserById(String id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty();
    }



    @Override
    public boolean deleteAllUsers() throws BusinessLogic {
        if ( userRepository.findAll().size() == 0  ){
            throw new BusinessLogic("There are no users in here!");
        }
        userRepository.deleteAll();

        return userRepository.findAll().isEmpty();
    }
}
