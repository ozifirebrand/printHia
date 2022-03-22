package ozi.app.printer.data.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class PrintUser {
    @Id
    @GeneratedValue(generator= "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @OneToMany
    private List<PrintOrder> orders;
}
