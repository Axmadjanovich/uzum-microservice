package uz.nt.productservice;

import dto.ResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.service.NumberService;
import uz.nt.productservice.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private NumberService numberService;
    @Autowired
    private ProductService productService;
    @Test
    void calculate_two_number_when_equal() {
        Integer result = numberService.calculate(2, 2);

        Assertions.assertEquals(result, 0, "Add two nums method works incorrectly!");
    }

    @Test
    void calculate_two_number_when_first_number_is_greater_than_second() {
        Integer result = numberService.calculate(11, 4);

        Assertions.assertEquals(result, 11/4, "Number must be divided!");
    }
    @Test
    void calculate_two_numbers_when_first_number_is_greater_and_negative() {
        assertThrows(NumberFormatException.class, ()-> numberService.calculate(0, -12));
        assertThrowsExactly(NumberFormatException.class, ()-> numberService.calculate(0,-12), "Error");
    }

    @Test
    @DisplayName(" ")
    void addProductWithValidationError() throws IOException{
        ProductDto product = new ProductDto();
        product.setAmount(100);
        product.setName("Xurmo");
        product.setDescription("Misr xurmosi");
        product.setPrice(55000);
        product.setImage(new MultipartFile("xurmo.jpg", "xurmo.jpg", "image/jpeg", getClass().getClassLoader().getResourceAsStream("xurmo.jpg")));

        ResponseDto<ProductDto> response = productService.addNewProduct(product);
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getId());
        assertEquals(response.getData().getName(), product.getName());

    }

}
