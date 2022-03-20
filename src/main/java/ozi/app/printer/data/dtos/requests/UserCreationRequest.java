package ozi.app.printer.data.dtos.requests;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String firstName;
    private String lastName;

    private String username;

    private String password;

    private String email;
}
