package ozi.app.printer.services.adminService;

import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;

import java.util.List;

public interface AdminServices {
    AdminCreationResponse createAdmin (AdminCreationRequest request);
    PrintAdmin getAdminById(String id);
    PrintAdmin getAdminByEmail(String id);
    PrintAdmin getAdminByUsername(String username);
    List<PrintAdmin> getAllAdmins();
    boolean deleteAdminById(String id);
    boolean deleteAllAdmins();
}