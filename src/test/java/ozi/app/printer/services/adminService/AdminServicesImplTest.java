package ozi.app.printer.services.adminService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.AdminCreationRequest;
import ozi.app.printer.data.dtos.responses.AdminCreationResponse;
import ozi.app.printer.exceptions.AdminExceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AdminServicesImplTest {
    @Autowired
    private AdminServices services;

    private AdminCreationRequest request;

    @BeforeEach
    public void setup(){
    }

    @AfterEach
    public void tearDown(){
    }

    @Test
    public void createAdmin() {
        //given...
        request  = new AdminCreationRequest();
        request.setEmail("mymail.com");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("mypassword");
        request.setPhoneNumber("11114444");
        request.setUsername("username");
        //when
        AdminCreationResponse response = services.createAdmin(request);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
        assertThat(response.getUsername()).isEqualTo(request.getUsername());
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testIncompleteDetails_ThrowsException(){
        //given ...
        request  = new AdminCreationRequest();
        request.setEmail("mmail.com");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("mypassword");
        request.setPhoneNumber("11114444");
        //when
        //assert
        assertThatThrownBy(()->services.createAdmin(request))
                .isInstanceOf(AdminExceptions.class)
                .hasMessage("These details are incomplete");

    }
    @Test
    public void getAdminById() {
    }

    @Test
    void getAdminByEmail() {
    }

    @Test
    public void getAdminByUsername() {
    }

    @Test
    public void getAllAdmins() {
    }

    @Test
    public void deleteAdminById() {
    }

    @Test
    public void deleteAllAdmins() {
    }
}