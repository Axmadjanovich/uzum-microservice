package uz.nt.productservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.productservice.UnitTest.AddNum;
import uz.nt.productservice.exeptions.NegativeNumberException;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private AddNum addNum;
    @Test
    @DisplayName("Calculate two numbers when they are equal")
    void addNumTest() {
        Integer res = addNum.calculate(6, 2);

        Assertions.assertEquals(res, 3, "Incorrect");
    }
    @Test
    void calculate_first_negative(){
        Assertions.assertThrows(NegativeNumberException.class, () -> addNum.calculate(0,-12));
    }

}
