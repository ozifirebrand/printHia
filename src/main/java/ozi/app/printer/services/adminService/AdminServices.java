package ozi.app.printer.services.adminService;

import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;
import ozi.app.printer.exceptions.AdminException;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.util.List;

public interface AdminServices {
    AdminCreationResponse createAdmin (AdminCreationRequest request) throws AdminException;
    PrintAdmin getAdminById(String id) throws AdminException, BusinessLogicException;
    PrintAdmin getAdminByEmail(String email) throws BusinessLogicException;
    PrintAdmin getAdminByUsername(String username);
    List<PrintAdmin> getAllAdmins();
    boolean deleteAdminById(String id);
    boolean deleteAllAdmins();
}
