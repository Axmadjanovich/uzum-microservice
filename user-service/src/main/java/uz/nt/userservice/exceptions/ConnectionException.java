package uz.nt.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.net.ConnectException;

@Getter
@Setter
public class ConnectionException extends RuntimeException {
    private String field;
    private String message;

    public ConnectionException(String field, String message){
        this.field = field;
        this.message = message;
    }
}
