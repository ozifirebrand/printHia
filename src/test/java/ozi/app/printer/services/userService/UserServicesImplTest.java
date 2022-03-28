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
    }

    @Test
    void createUser() throws BusinessLogicException {

        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
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
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingLastname() {
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setPassword("password");
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingFirstname(){
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setLastName("lastname");
        request.setPassword("password");
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    void getUserById() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
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
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
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
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");

        UserCreationRequest request1 = new UserCreationRequest();
        request1.setEmail("firstname1@mail.com");
        request1.setUsername("username1");
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
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
        UserCreationResponse response = userServices.createUser(request);

        //when
        boolean userIsDeleted = userServices.deleteUserById(response.getId());

        //assert
        assertThat(userIsDeleted).isTrue();
    }

    @Test
    public void deleteAllUsers() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
        UserCreationRequest request1 = new UserCreationRequest();
        request1.setEmail("firstname1@mail.com");
        request1.setUsername("username1");
        request1.setFirstName("firstname1");
        request1.setLastName("lastname1");
        request1.setPassword("password1");
        //when
        userServices.createUser(request);
        userServices.createUser(request1);
        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);

        //when
        userServices.deleteAllUsers();

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(0);
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
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
        //when
        UserCreationResponse response = userServices.createUser(request);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getRole()).isEqualTo(Role.USER);
        assertThat(response.getUsername()).isEqualTo(request.getUsername());
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getId()).isNotNull();
    }
}