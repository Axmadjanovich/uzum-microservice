package uz.nt.productservice.exeptions;

public class NegativeNumberException extends NumberFormatException {
    int num;
    public NegativeNumberException(String message, int num){
        super(message);
        this.num = num;
    }
}
