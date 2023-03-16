package uz.nt.productservice;

import dto.ResponseDto;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.productservice.UnitTest.AddNum;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.exeptions.NegativeNumberException;
import uz.nt.productservice.service.ProductService;
import validator.AppStatusMessages;

import java.io.IOException;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private AddNum addNum;
    @Autowired
    ProductService productService;
    @Test
    @DisplayName("Calculate two numbers when they are equal")
    void addNumTest() {
        Integer res = addNum.calculate(6, 2);

        assertEquals(res, 3, "Incorrect");
    }
    @Test
    void calculate_first_negative(){
        assertThrows(NegativeNumberException.class, () -> addNum.calculate(0,-12));
    }
    @Test
    void addProductWithValidationError() throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setName("Xuro");
        productDto.setAmount(20);

        ResponseDto<ProductDto> response = productService.addNewProduct(productDto);
        assertEquals(response.getCode(), -2);
        assertEquals(response.getMessage(), AppStatusMessages.VALIDATION_ERROR);
        assertFalse(response.isSuccess());

        int size = response.getErrors().size();

        assertEquals(size, 2);

    }

}
