package uz.nt.productservice.exeptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegativeNumberException extends NumberFormatException{
    int number;
    public NegativeNumberException(String message, int number){
        super(message);
        this.number = number;
    }
}
