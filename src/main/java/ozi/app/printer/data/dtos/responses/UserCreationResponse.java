package ozi.app.printer.data.dtos.responses;

import lombok.Data;

@Data
public class UserCreationResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
