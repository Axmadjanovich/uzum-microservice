package uz.nt.productservice;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.productservice.exceptions.NegativeNumberException;
import uz.nt.productservice.service.validator.NumberService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private NumberService numberService;

    @Test
    @DisplayName("Calculate two numbers when they are equal")
    void calculate_two_numbers_when_equal() {
        Integer result = numberService.calculate(4, 4);
        assertEquals(result, 0, "Add two nums method works incorrectly!");
    }

    @Test
    void calculate_two_number_when_first_number_is_greater_than_second() {
        Integer result = numberService.calculate(12, 3);
        assertEquals(result, 4, "Numbers must be divided!");
    }

    @Test
    @DisplayName("Must throw Exception")
    void calculate_two_numbers_when_first_number_is_greater_and_negative() {
        assertAll(
                () -> assertThrows(NegativeNumberException.class, () -> numberService.calculate(0, -3), "Here must throw exception"),
//                () -> assertThrowsExactly(NumberFormatException.class, () -> numberService.calculate(0, -3), "Here must throw exception and this is NumberFormatException"),
                () -> assertThrowsExactly(NegativeNumberException.class, () -> numberService.calculate(0, -3), "This is NegativeNumberFormatException in assertAll")
        );
    }


}
