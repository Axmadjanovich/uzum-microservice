package uz.nt.productservice.exeptions;

public class NegativeNumberException extends NumberFormatException{
    private int number;

    public NegativeNumberException() {
        super(s);
        this.number = number;
    }
}
