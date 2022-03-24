package ozi.app.printer.services.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;
import ozi.app.printer.data.repositories.AdminRepository;
import ozi.app.printer.exceptions.AdminException;
import ozi.app.printer.mapper.Mapper;

import java.util.List;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public AdminCreationResponse createAdmin(AdminCreationRequest request) throws AdminException {
        validate(request);
        PrintAdmin admin = Mapper.map(request);
        PrintAdmin savedAdmin = adminRepository.save(admin);
        return Mapper.map(savedAdmin);
    }
    private void validate(AdminCreationRequest request) throws AdminException {
        boolean firstNameIsEmpty= request.getFirstName() == null;
        boolean lastNameIsEmpty= request.getLastName() == null;
        boolean emailIsEmpty= request.getEmail() == null;
        boolean usernameIsEmpty= request.getUsername() == null;
        boolean phoneNumberIsEmpty= request.getPhoneNumber() == null;
        boolean passwordIsEmpty= request.getPassword() == null;

        if ( firstNameIsEmpty||lastNameIsEmpty||emailIsEmpty|| usernameIsEmpty||phoneNumberIsEmpty||passwordIsEmpty)
            throw new AdminException("These details are incomplete");
    }

    @Override
    public PrintAdmin getAdminById(String id) {
        return null;
    }

    @Override
    public PrintAdmin getAdminByEmail(String id) {
        return null;
    }

    @Override
    public PrintAdmin getAdminByUsername(String username) {
        return null;
    }

    @Override
    public List<PrintAdmin> getAllAdmins() {
        return null;
    }

    @Override
    public boolean deleteAdminById(String id) {
        return false;
    }

    @Override
    public boolean deleteAllAdmins() {
        return false;
    }
}
