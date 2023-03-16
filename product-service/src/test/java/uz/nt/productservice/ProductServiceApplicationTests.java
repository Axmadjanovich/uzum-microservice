package uz.nt.productservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.productservice.exeptions.NegativeNumberException;
import uz.nt.productservice.service.NumberService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private NumberService numberService;
    @Test
    void calculate_two_numbers_when_equal() {
        Integer result = numberService.calculate(2,2);

        assertEquals(result, 0, "Add two nums method works incorrectly!");
    }

    @Test
    void calculate_two_number_when_first_number_is_greater_than_second(){
        Integer result = numberService.calculate(11, 4);

        assertEquals(result, 11 / 4, "Number must be divided!");
    }

    @Test
    void calculate_two_numbers_when_first_number_is_greater_and_negative(){
        assertThrows(NumberFormatException.class, () -> numberService.calculate(0, -12));
        assertThrowsExactly(NegativeNumberException.class, () -> numberService.calculate(0, -12), "Expected NegativeNumberException");
    }

//    void addProductWithValidationError
}
