package ozi.app.printer.data.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ozi.app.printer.data.models.PrintAdmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    void findByEmail() {
        //given
        PrintAdmin admin = new PrintAdmin();
        admin.setPhoneNumber("849384");
        admin.setEmail("myemail.com");
        admin.setFirstName("firstname");
        admin.setLastName("lastName");
        admin.setUsername("username");
        admin.setPassword("password");
        PrintAdmin savedAdmin = repository.save(admin);

        assertThat(savedAdmin.getId()).isNotNull();
        assertThat(savedAdmin.getUsername()).isEqualTo(admin.getUsername());
        assertThat(savedAdmin.getEmail()).isEqualTo(admin.getEmail());
        assertThat(savedAdmin.getPhoneNumber()).isEqualTo(admin.getPhoneNumber());

        //when
        PrintAdmin foundAdmin = repository.findByEmail(savedAdmin.getEmail());

        //assert
        assertThat(foundAdmin.getId()).isNotNull();
        assertThat(foundAdmin.getUsername()).isEqualTo(admin.getUsername());
        assertThat(foundAdmin.getEmail()).isEqualTo(admin.getEmail());
        assertThat(foundAdmin.getPhoneNumber()).isEqualTo(admin.getPhoneNumber());
    }
}