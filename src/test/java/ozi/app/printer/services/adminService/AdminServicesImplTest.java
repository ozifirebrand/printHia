package ozi.app.printer.services.adminService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.data.models.PrintAdmin;
import ozi.app.printer.data.models.Role;
import ozi.app.printer.exceptions.AdminException;
import ozi.app.printer.exceptions.BusinessLogicException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AdminServicesImplTest {
    @Autowired
    private AdminServices services;

    private AdminCreationRequest request;

    @BeforeEach
    public void setup(){
        request  = new AdminCreationRequest();
        request.setEmail("mymail.com");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("mypassword");
        request.setPhoneNumber("11114444");
    }

    @AfterEach
    public void tearDown(){
    }

    @Test
    public void createAdmin() throws BusinessLogicException {
        //given...
        //when
        AdminCreationResponse response = services.createAdmin(request);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testIncompleteDetails_ThrowsException(){
        //given ...
        request.setEmail(null);
        //when
        //assert
        assertThatThrownBy(()->services.createAdmin(request))
                .isInstanceOf(AdminException.class)
                .hasMessage("These details are incomplete");

    }

    @Test
    public void updateAdmin() {
    }

    @Test
    public void getAdminById() throws BusinessLogicException {
        //given
        AdminCreationResponse response = services.createAdmin(request);

        //when
        PrintAdmin admin = services.getAdminById(response.getId());

        //assert
        assertThat(admin.getId()).isEqualTo(response.getId());
        assertThat(admin.getEmail()).isEqualTo(response.getEmail());
        assertThat(admin.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(admin.getLastName()).isEqualTo(response.getLastName());
        assertThat(admin.getPhoneNumber()).isNotNull();


    }

    @Test
    public void test_InvalidIdThrows_NoSuchUserException (){

        //assert
        assertThatThrownBy(()->services.getAdminById("an_invalid_id"))
                .isInstanceOf(AdminException.class)
                .hasMessage("No such user with id an_invalid_id exists!");
    }

    @Test
    void getAdminByEmail() throws BusinessLogicException {
        //given...
        request.setEmail("myownemail.com");
        AdminCreationResponse response = services.createAdmin(request);

        //when
        PrintAdmin admin = services.getAdminByEmail(response.getEmail());

        //assert
        assertThat(admin).isNotNull();
        assertThat(admin.getEmail()).isEqualTo(response.getEmail());
        assertThat(admin.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(admin.getLastName()).isEqualTo(response.getLastName());
        assertThat(admin.getId()).isEqualTo(response.getId());
        assertThat(admin.getPhoneNumber()).isEqualTo(request.getPhoneNumber());
    }

    @Test
    public void test_InvalidEmailThrows_NoSuchUserException (){

        //assert
        assertThatThrownBy(()->services.getAdminByEmail("an_invalid_email"))
                .isInstanceOf(AdminException.class)
                .hasMessage("No such user with email an_invalid_email exists!");
    }

    @Test
    public void getAdminByUsername() throws BusinessLogicException {
        //given
        request.setEmail("myownemail2.com");
        AdminCreationResponse response = services.createAdmin(request);

        //when
        PrintAdmin admin = services.getAdminByUsername(response.getEmail());

        //assert
        assertThat(admin).isNotNull();
        assertThat(admin.getEmail()).isEqualTo(request.getEmail());
        assertThat(admin.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(admin.getLastName()).isEqualTo(request.getLastName());
        assertThat(admin.getId()).isEqualTo(response.getId());
        assertThat(admin.getPhoneNumber()).isEqualTo(request.getPhoneNumber());
    }

    @Test
    public void test_IfEmptyUsername_ThrowEmptyUsernameInput(){
        //assert
        assertThatThrownBy(()->services.getAdminByUsername(""))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("This username cannot be empty!");
    }

    @Test
    public void test_IfNullUsername_ThrowEmptyUsernameInput(){
        //assert
        assertThatThrownBy(()->services.getAdminByUsername(null))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("This username cannot be empty!");
    }

    @Test
    public void getAllAdmins() throws BusinessLogicException{
        //given
        AdminCreationRequest request1 = new AdminCreationRequest();
        request1.setEmail("myownmail3.com");
        request1.setFirstName("firstname");
        request1.setLastName("lastname");
        request1.setPassword("mypassword");
        request1.setPhoneNumber("11114444");

        AdminCreationResponse response = services.createAdmin(request);
        AdminCreationResponse response1 = services.createAdmin(request1);

        //when
        List<PrintAdmin> admins = services.getAllAdmins();

        //assert
        assertThat(admins.size()).isEqualTo(2);
        assertThat(admins.get(0).getEmail()).isEqualTo(response.getEmail());
        assertThat(admins.get(0).getFirstName()).isEqualTo(response.getFirstName());
        assertThat(admins.get(0).getLastName()).isEqualTo(response.getLastName());
        assertThat(admins.get(0).getId()).isEqualTo(response.getId());
        assertThat(admins.get(1).getEmail()).isEqualTo(response1.getEmail());
        assertThat(admins.get(1).getFirstName()).isEqualTo(response1.getFirstName());
        assertThat(admins.get(1).getLastName()).isEqualTo(response1.getLastName());
        assertThat(admins.get(1).getId()).isEqualTo(response1.getId());

    }

    @Test
    public void test_IfEmptyList_ThrowError() throws AdminException {
        //given...
        services.deleteAllAdmins();
        //when...
        //assert

        assertThatThrownBy(()->services.getAllAdmins())
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no admins here!");
    }

    @Test
    public void deleteAdminById() throws BusinessLogicException {
        //given
        AdminCreationResponse response = services.createAdmin(request);

        //when
        boolean isDeleted = services.deleteAdminById(response.getId());

        //assert
        assertThat(isDeleted).isTrue();
        assertThatThrownBy(()->services.getAdminById(response.getId()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("No such user with id " +response.getId()+" exists!");

    }

    @Test
    public void deleteAllAdmins() throws BusinessLogicException {
        //given

        AdminCreationRequest request1 = new AdminCreationRequest();
        request1.setEmail("myownmail3.com");
        request1.setFirstName("firstname");
        request1.setLastName("lastname");
        request1.setPassword("mypassword");
        request1.setPhoneNumber("11114444");

        services.createAdmin(request);
        services.createAdmin(request1);

        //when
        boolean noAdmin = services.deleteAllAdmins();

        //assert
        assertThat(noAdmin).isTrue();
        assertThatThrownBy(()->services.getAllAdmins())
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no admins here!");
    }

    @Test
    public void test_ThrowException_IfDeleteAll_OnEmptyDB(){
        //assert
        assertThatThrownBy(()->services.deleteAllAdmins())
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no admins to delete!");
    }

    @Test
    public void test_UserRoleIsAdmin() throws BusinessLogicException {
        //given
        AdminCreationResponse response = services.createAdmin(request);

        assertThat(response).isNotNull();
        assertThat(response.getRole()).isEqualTo(Role.ADMIN);
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getId()).isNotNull();
    }
}