package ozi.app.printer.data.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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

    private String password;

    @Column(unique = true)
    private String email;

    private Role role;

    @OneToMany
    @Fetch(FetchMode.JOIN)
    private List<PrintOrder> orders;
}
