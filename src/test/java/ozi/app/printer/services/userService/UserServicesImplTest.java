package ozi.app.printer.services.userService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printer.data.dtos.requests.UserCreationRequest;
import ozi.app.printer.data.dtos.responses.UserCreationResponse;
import ozi.app.printer.data.models.PrintUser;
import ozi.app.printer.exceptions.BusinessLogic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServicesImplTest {

    @Autowired
    private UserServicesImpl userServices;

    private UserCreationRequest request;

    @BeforeEach
    void setUp() {
       request = new UserCreationRequest();
    }

    @AfterEach
    void tearDown() {
        userServices.deleteAllUsers();
    }

    @Test
    void createUser() throws BusinessLogic {

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
    }

    @Test
    public void assert_CompleteUserDetails_BeforeSavingUser_MissingPassword(){
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogic.class).hasMessage("Incomplete details");
    }
    @Test
    public void assert_CompleteUserDetails_BeforeSavingUser_MissingLastname(){
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setPassword("password");
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogic.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_CompleteUserDetails_BeforeSavingUser_MissingFirstname(){
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setLastName("lastname");
        request.setPassword("password");
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogic.class).hasMessage("Incomplete details");
    }


    @Test
    void getUserById() throws BusinessLogic {
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
    }

    @Test
    public void test_ifIdIsEmpty_FindByIdThrows_UserDoesNotExistErrorMessage() throws BusinessLogic {
        //given no condition
        //when
        //assert
        assertThatThrownBy(()->userServices.getUserById("just_any_value")).isInstanceOf(BusinessLogic.class).hasMessage("This user does not exist!");
    }

    @Test
    void getUserByEmail() throws BusinessLogic {
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
    }

    @Test
    public void test_ifEmailIsEmpty_FindByEmailThrows_UserWithEmailDoesNotExistErrorMessage() throws BusinessLogic {
        //given no condition
        //when
        //assert
        assertThatThrownBy(()->userServices
                .getUserByEmail("invalidemail@mail.com"))
                .isInstanceOf(BusinessLogic.class)
                .hasMessage("This user with email \"invalidemail@mail.com\" does not exist!");
    }


                                    //todo TEST FOR INVALID EMAIL VALUE - REGEX



    @Test
    void getAllUsers() throws BusinessLogic {
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
        UserCreationResponse response = userServices.createUser(request);
        UserCreationResponse response1 = userServices.createUser(request1);

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);

    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserById() throws BusinessLogic {
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
    void deleteUserByEmail() throws BusinessLogic {
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
        UserCreationResponse response = userServices.createUser(request);

        //when
        boolean userIsDeleted = userServices.deleteUserByEmail(response.getEmail());

        //assert
        assertThat(userIsDeleted).isTrue();
    }

    @Test
    void deleteUserByUsername() throws BusinessLogic {
        //given
        request.setEmail("firstname@mail.com");
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
        UserCreationResponse response = userServices.createUser(request);

        //when
        boolean userIsDeleted = userServices.deleteUserByUsername(response.getUsername());

        //assert
        assertThat(userIsDeleted).isTrue();
    }

    @Test
    void deleteAllUsers() throws BusinessLogic {
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
        UserCreationResponse response = userServices.createUser(request);
        UserCreationResponse response1 = userServices.createUser(request1);
        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);

        //when
        userServices.deleteAllUsers();

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(0);
    }
}