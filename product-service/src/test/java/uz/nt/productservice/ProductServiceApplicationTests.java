package uz.nt.productservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.productservice.exceptions.NegativeNumberException;
import uz.nt.productservice.service.NumberService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private NumberService numberService;
    @Test
    void calculate_first_number_is_greater_than_second() {
        Integer result = numberService.calculate(10, 5);
        assertEquals(result, 2, "Wrong output from first condition");
    }

    @Test
    @DisplayName("Calculate when equel method")
    void calculate_when_equal_condition(){
        Integer result = numberService.calculate(2, 2);
        assertEquals(result, 0, "Add when equal functioning wrong");
    }

    @Test
    void calculate_when_firstNum_is_greater_and_second_num_is_negative(){
        assertThrows(NumberFormatException.class, () -> numberService.calculate(0 , -20));
        assertThrowsExactly(NumberFormatException.class, () -> numberService.calculate(0, -30), "Expected NegativeNumberException but output was different");
        assertThrowsExactly(NegativeNumberException.class, () -> numberService.calculate(0, -50), "Expected NegativeNumberException but output was wrong");
    }

}
