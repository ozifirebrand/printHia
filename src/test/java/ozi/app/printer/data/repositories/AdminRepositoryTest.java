package ozi.app.printer.data.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ozi.app.printer.data.models.PrintAdmin;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class AdminRepositoryTest {

    @Autowired
    private AdminRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void findAll(){
        //given
        PrintAdmin admin = new PrintAdmin();
        admin.setPassword("mupassword");
        admin.setEmail("myemail.com");
        admin.setLastName("mylastname");
        admin.setPhoneNumber("9849300");
        admin.setFirstName("myfirstname");

        //and

        PrintAdmin admin1 = new PrintAdmin();
        admin1.setPassword("mpassword");
        admin1.setEmail("memail.com");
        admin1.setLastName("mlastname");
        admin1.setPhoneNumber("98949300");
        admin1.setFirstName("mfirstname");

        PrintAdmin savedAdmin = repository.save(admin);
        PrintAdmin savedAdmin1 = repository.save(admin1);

        assertThat(savedAdmin.getPassword()).isEqualTo(admin.getPassword());
        assertThat(savedAdmin.getEmail()).isEqualTo(admin.getEmail());
        assertThat(savedAdmin.getLastName()).isEqualTo(admin.getLastName());
        assertThat(savedAdmin.getPhoneNumber()).isEqualTo(admin.getPhoneNumber());
        assertThat(savedAdmin.getFirstName()).isEqualTo(admin.getFirstName());

        assertThat(savedAdmin1.getPassword()).isEqualTo(admin1.getPassword());
        assertThat(savedAdmin1.getEmail()).isEqualTo(admin1.getEmail());
        assertThat(savedAdmin1.getLastName()).isEqualTo(admin1.getLastName());
        assertThat(savedAdmin1.getPhoneNumber()).isEqualTo(admin1.getPhoneNumber());
        assertThat(savedAdmin1.getFirstName()).isEqualTo(admin1.getFirstName());

        //when
        List<PrintAdmin> admins = repository.findAll();

        //assert
        assertThat(admins.size()).isEqualTo(2);
        assertThat(admins.get(0).getId()).isEqualTo(savedAdmin.getId());
        assertThat(admins.get(0).getPassword()).isEqualTo(savedAdmin.getPassword());
        assertThat(admins.get(0).getLastName()).isEqualTo(savedAdmin.getLastName());
        assertThat(admins.get(0).getFirstName()).isEqualTo(savedAdmin.getFirstName());
        assertThat(admins.get(0).getEmail()).isEqualTo(savedAdmin.getEmail());

        assertThat(admins.get(1).getId()).isEqualTo(savedAdmin1.getId());
        assertThat(admins.get(1).getPassword()).isEqualTo(savedAdmin1.getPassword());
        assertThat(admins.get(1).getLastName()).isEqualTo(savedAdmin1.getLastName());
        assertThat(admins.get(1).getFirstName()).isEqualTo(savedAdmin1.getFirstName());
        assertThat(admins.get(1).getEmail()).isEqualTo(savedAdmin1.getEmail());
    }
    @Test
    void findByEmail() {
        //given
        PrintAdmin admin = new PrintAdmin();
        admin.setPhoneNumber("849384");
        admin.setEmail("myemail.com");
        admin.setFirstName("firstname");
        admin.setLastName("lastName");
        admin.setPassword("password");
        PrintAdmin savedAdmin = repository.save(admin);

        assertThat(savedAdmin.getId()).isNotNull();
        assertThat(savedAdmin.getEmail()).isEqualTo(admin.getEmail());
        assertThat(savedAdmin.getPhoneNumber()).isEqualTo(admin.getPhoneNumber());

        //when
        PrintAdmin foundAdmin = repository.findByEmail(savedAdmin.getEmail());

        //assert
        assertThat(foundAdmin.getId()).isNotNull();
        assertThat(foundAdmin.getEmail()).isEqualTo(admin.getEmail());
        assertThat(foundAdmin.getPhoneNumber()).isEqualTo(admin.getPhoneNumber());
    }
}