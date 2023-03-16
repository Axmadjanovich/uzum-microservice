package uz.nt.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailServiceConnectionException extends Exception{
    private String message;

    public EmailServiceConnectionException(String message){
        this.message = message;
    }
}
