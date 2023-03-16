package uz.nt.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConnectionException extends Exception{
    private String message;

    public DatabaseConnectionException(String message){
        this.message = message;
    }
}
