package ozi.app.printer.services.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.OrderCreationRequest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.OrderCreationResponse;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.models.Role;
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
        boolean firstNameIsEmpty = request.getFirstName() == null;
        boolean lastNameIsEmpty = request.getLastName() == null;
        boolean passwordIsEmpty = request.getPassword() == null;

        if ( emailIsEmpty || firstNameIsEmpty|| lastNameIsEmpty|| passwordIsEmpty ){
            throw new BusinessLogicException("Incomplete details");
        }
    }

    @Override
    public PrintUser getUserById(String userId) throws BusinessLogicException {
        Optional<PrintUser> optionalPrintUser = userRepository.findById(userId);
        if ( optionalPrintUser.isPresent() ) {
            return optionalPrintUser.get();
        }
        throw new BusinessLogicException("This user does not exist!");
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
    public boolean deleteUserById(String userId) {
        userRepository.deleteById(userId);
        final boolean empty = userRepository.findById(userId).isEmpty();
        return empty;
    }

    @Override
    public boolean deleteAllUsers() throws BusinessLogicException {
        if ( userRepository.findAll().size() == 0  ){
            throw new BusinessLogicException("There are no users in here!");
        }
        userRepository.deleteAll();
        return userRepository.findAll().size()==0;
    }

    @Override
    public void updateUserRole(String userId, Role role) {
        Optional<PrintUser> optionalPrintUser =userRepository.findById(userId);
        PrintUser printUser = new PrintUser();
        if ( optionalPrintUser.isPresent() ) printUser = optionalPrintUser.get();
        printUser.setRole(role);
        userRepository.save(printUser);
    }

}
