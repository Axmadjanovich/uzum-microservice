package uz.nt.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegativeNumberException extends NumberFormatException{
    private int number;

    public NegativeNumberException(String message, int number){
        super(message);
        this.number = number;
    }
}
