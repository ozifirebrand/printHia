package ozi.app.printer.services.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;
import ozi.app.printer.data.repositories.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public AdminCreationResponse createAdmin(AdminCreationRequest request) {
        AdminCreationResponse response = new AdminCreationResponse();
        PrintAdmin admin = new PrintAdmin();
        admin.setEmail(request.getEmail());
        admin.setFirstName(request.getFirstName());
        admin.setUsername(request.getUsername());
        admin.setLastName(request.getLastName());
        PrintAdmin savedAdmin = adminRepository.save(admin);
        response.setId(savedAdmin.getId());
        response.setUsername(savedAdmin.getUsername());
        response.setEmail(savedAdmin.getEmail());
        response.setFirstName(savedAdmin.getFirstName());
        response.setLastName(savedAdmin.getLastName());
        return response;
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
