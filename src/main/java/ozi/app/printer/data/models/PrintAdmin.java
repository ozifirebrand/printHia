package ozi.app.printer.data.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Data
public class PrintAdmin {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(strategy="uuid", name="system-uuid")
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Column(unique= true)
    private String email;

    private Role role;

    private String password;
}
