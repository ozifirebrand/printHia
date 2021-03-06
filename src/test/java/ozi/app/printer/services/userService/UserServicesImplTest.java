package ozi.app.printer.services.userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.data.models.Role;
import ozi.app.printer.exceptions.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServicesImplTest {

    @Autowired
    @Mock
    private UserServices userServices;

    private UserCreationRequest request;

    @BeforeEach
    void setUp() {
       request = new UserCreationRequest();

        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
    }

    @Test
    void createUser() throws BusinessLogicException {

        //given
        request.setEmail("firstname@mail.com");
        //when
        UserCreationResponse response=userServices.createUser(request);
        //assert
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("firstname@mail.com");
        assertThat(response.getFirstName()).isEqualTo("firstname");
        assertThat(response.getLastName()).isEqualTo("lastname");
        assertThat(response.getOrders().size()).isEqualTo(0);
        userServices.deleteAllUsers();
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingPassword() {
        //given
        request.setEmail("firstname@mail1.com");
        request.setPassword(null);
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingLastname() {
        //given
        request.setEmail("firstname@mail2.com");
        request.setLastName(null);
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingFirstname(){
        //given
        request.setEmail("firstname@mail3.com");
        request.setFirstName(null);
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    void getUserById() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail4.com");
        UserCreationResponse response = userServices.createUser(request);
        //when
        PrintUser user = userServices.getUserById(response.getId());
        //assert
        assertThat(user.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(user.getLastName()).isEqualTo(response.getLastName());
        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertThat(user.getId()).isEqualTo(response.getId());
        userServices.deleteAllUsers();

    }

    @Test
    public void test_ifIdIsEmpty_FindByIdThrows_UserDoesNotExistErrorMessage() {
        //given no condition
        //when
        //assert
        assertThatThrownBy(()->userServices.getUserById("just_any_value")).isInstanceOf(BusinessLogicException.class).hasMessage("This user does not exist!");
    }

    @Test
    void getUserByEmail() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail5.com");
        UserCreationResponse response = userServices.createUser(request);

        //when
        PrintUser user = userServices.getUserByEmail(response.getEmail());

        //assert

        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertThat(user.getId()).isEqualTo(response.getId());
        assertThat(user.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(user.getLastName()).isEqualTo(response.getLastName());
        userServices.deleteAllUsers();

    }

    @Test
    public void test_ifEmailIsEmpty_FindByEmailThrows_UserWithEmailDoesNotExistErrorMessage(){
        //given no condition
        //when
        //assert
        assertThatThrownBy(()->userServices
                .getUserByEmail("invalidemail@mail.com"))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("This user with email \"invalidemail@mail.com\" does not exist!");
    }


                                    //todo TEST FOR INVALID EMAIL VALUE - REGEX

    @Test
    public void updateUser() {
    }

    @Test
    public void getAllUsers() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail6.com");

        UserCreationRequest request1 = new UserCreationRequest();
        request1.setEmail("firstname1@mail7.com");
        request1.setFirstName("firstname1");
        request1.setLastName("lastname1");
        request1.setPassword("password1");


        //when
       userServices.createUser(request);
       userServices.createUser(request1);

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);
        userServices.deleteAllUsers();

    }


    @Test
    public void deleteUserById() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail8.com");
        UserCreationResponse response = userServices.createUser(request);

        //when
        boolean userIsDeleted = userServices.deleteUserById(response.getId());

        //assert
        assertThat(userIsDeleted).isTrue();
    }

    @Test
    public void deleteAllUsers() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail9.com");
        UserCreationRequest request1 = new UserCreationRequest();
        request1.setEmail("firstname1@mail01.com");
        request1.setFirstName("firstname2");
        request1.setLastName("lastname2");
        request1.setPassword("password3");
        //when
        userServices.createUser(request);
        userServices.createUser(request1);
        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);

        //when
        boolean isAllDeleted = userServices.deleteAllUsers();

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(0);
        assertThat(isAllDeleted).isTrue();
    }

    @Test
    public void test_ThrowException_WhenDeleteAllOnEmptyDB() throws BusinessLogicException {
        userServices.deleteAllUsers();
        //given
        //when
        //assert
        assertThatThrownBy(()->userServices.deleteAllUsers()).isInstanceOf(BusinessLogicException.class).hasMessage("There are no users in here!");
    }


    @Test
    public void test_UserRoleIsUser() throws BusinessLogicException {

        //given
        request.setEmail("firstname@mail02.com");
        //when
        UserCreationResponse response = userServices.createUser(request);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getRole()).isEqualTo(Role.USER);
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testUpdateUserRole() throws BusinessLogicException {
        //given...
        request.setEmail("firstname@mail03.com");
        UserCreationResponse response = userServices.createUser(request);

        //when
        Role role = response.getRole();

        //assert
        assertThat(role).isEqualTo(Role.USER);

        //when
        userServices.updateUserRole(response.getId(), Role.ADMIN);

        //assert
        assertThat(userServices.getUserById(response.getId()).getRole()).isEqualTo(Role.ADMIN);

    }
}