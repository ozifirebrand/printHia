package ozi.app.printer.data.dtos.responses;

import lombok.Data;

@Data
public class AdminCreationResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;
}
