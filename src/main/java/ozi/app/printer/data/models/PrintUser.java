package ozi.app.printer.data.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class PrintUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @OneToMany
    private List<PrintOrder> orders;
}
