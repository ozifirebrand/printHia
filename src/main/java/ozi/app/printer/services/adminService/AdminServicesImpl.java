package ozi.app.printer.services.adminService;

import org.springframework.stereotype.Service;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;

import java.util.List;

@Service
public class AdminServicesImpl implements AdminServices {
    @Override
    public AdminCreationResponse createAdmin(AdminCreationRequest request) {
        return null;
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
