package ozi.app.printer.mapper;

import ozi.app.printer.data.dtos.requests.*;
import ozi.app.printer.data.models.PrintUser;

public class Mapper {
    public static PrintUser map (UserCreationRequest request){
        PrintUser user = new PrintUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;

    }
}
