package ozi.app.printer.mapper;

import ozi.app.printer.data.dtos.requests.*;
import ozi.app.printer.data.models.PrintUser;

public class Mapper {
    public PrintUser map (UserCreationRequest request){
        PrintUser user = new PrintUser();


        return user;

    }
}
