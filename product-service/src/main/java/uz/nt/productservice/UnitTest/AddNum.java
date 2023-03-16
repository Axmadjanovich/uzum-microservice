package uz.nt.productservice.UnitTest;

import org.springframework.stereotype.Component;
import uz.nt.productservice.exeptions.NegativeNumberException;

@Component
public class AddNum {
    public Integer calculate(Integer a, Integer b){
        if(a > b){
            if(a <= 0){
                throw new NegativeNumberException("Number is less than 0", a);
            }
            return a / b;
        }
        if(a == b) return 0;

        return a + b;
    }
}
