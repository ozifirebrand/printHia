package ozi.app.printer.data.dtos.responses;

import lombok.Data;
import ozi.app.printer.data.models.Role;

@Data
public class AdminCreationResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private Role role;
}
