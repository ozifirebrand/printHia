package ozi.app.printer.services.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.repositories.UserRepository;
import ozi.app.printer.exceptions.BusinessLogicException;
import ozi.app.printer.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserCreationResponse createUser(UserCreationRequest request) throws BusinessLogicException {
        validateRequestDetails(request);

        PrintUser user = Mapper.map(request);
        PrintUser savedUser = userRepository.save(user);
        return Mapper.map(savedUser);
    }

    private void validateRequestDetails(UserCreationRequest request) throws BusinessLogicException {
        boolean emailIsEmpty= request.getEmail() == null;
        boolean usernameIsEmpty= request.getUsername() == null;
        boolean firstNameIsEmpty = request.getFirstName() == null;
        boolean lastNameIsEmpty = request.getLastName() == null;
        boolean passwordIsEmpty = request.getPassword() == null;

        if ( emailIsEmpty || usernameIsEmpty|| firstNameIsEmpty|| lastNameIsEmpty|| passwordIsEmpty ){
            throw new BusinessLogicException("Incomplete details");
        }
    }

    @Override
    public PrintUser getUserById(String id) throws BusinessLogicException {
        Optional<PrintUser> optionalPrintUser = userRepository.findById(id);
        if ( optionalPrintUser.isEmpty() ){
            throw new BusinessLogicException("This user does not exist!");
        }
        return optionalPrintUser.get();
    }

    @Override
    public PrintUser getUserByEmail(String email) throws BusinessLogicException {
        Optional<PrintUser> optionalPrintUser = userRepository.findPrintUserByEmail(email);
        if ( optionalPrintUser.isEmpty() ){
            throw new BusinessLogicException("This user with email \""+email +"\" does not exist!");
        }
        return optionalPrintUser.get();
    }

    @Override
    public List<PrintUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUserById(String id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty();
    }

    @Override
    public boolean deleteAllUsers() throws BusinessLogicException {
        if ( userRepository.findAll().size() == 0  ){
            throw new BusinessLogicException("There are no users in here!");
        }
        userRepository.deleteAll();
        return userRepository.findAll().isEmpty();
    }
}
