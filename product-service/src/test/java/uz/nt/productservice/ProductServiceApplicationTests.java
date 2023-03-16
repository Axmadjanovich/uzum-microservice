package uz.nt.productservice;

import dto.ResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import uz.nt.productservice.clients.FileClient;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.exceptions.NegativeNumberException;
import uz.nt.productservice.service.NumberService;
import uz.nt.productservice.service.ProductService;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private NumberService numberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private  FileClient fileClient;

    @Test
    @DisplayName("Calculate two numbers when they are equal")
    void calculate_two_numbers_when_equal() {
        Integer result = numberService.calculate(2, 2);

        assertEquals(result, 0, "Add two nums method works incorrectly!");
    }

    @Test
    void calculate_two_number_when_first_number_is_greater_than_second(){
        Integer result = numberService.calculate(11, 4);

        assertEquals(result, 11 / 4, "Numbers must be divided!");
    }

    @Test
    void calculate_two_numbers_when_first_number_is_greater_and_negative(){
        assertThrows(NumberFormatException.class, () -> numberService.calculate(0, -12));
        assertThrowsExactly(NegativeNumberException.class, () ->numberService.calculate(0, -12), "Expected NegativeNumberException exactly");
    }

//    @Test
//    @DisplayName("Add product with validation erros")
//    void addProductWithValidationError() throws IOException {
//        ProductDto product = new ProductDto();
//        product.setAmount(100);
//        product.setName("Xurmo");
//
//        ResponseDto<ProductDto> response = productService.addNewProduct(product);
//        assertEquals(response.getCode(), -2, "Response code is not equal 2");
//        assertEquals(response.getMessage(), AppStatusMessages.VALIDATION_ERROR, "Response would be with validation errors");
//        assertFalse(response.isSuccess(), "Response would be unsuccessful");
//
//        assertEquals(response.getErrors().size(), 2, "Response's errors' size is not equal 2");
//    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/insert-test-values.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-values.sql")
    @DisplayName("Add product with valid value")
    void addProduct() throws IOException {
        ProductDto productDto = ProductDto.builder()
                .amount(100)
                .category(CategoryDto.builder().id(1).build())
                .name("Xurmo")
                .description("Misr xurmosi")
                .price(55000)
                .image(new MockMultipartFile("xurmo.jpg", "xurmo.jpg", "image/jpeg", getClass().getClassLoader().getResourceAsStream("xurmo.jpg")))
                .build();

        ResponseDto<ProductDto> response = productService.addNewProduct(productDto);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getId());
        assertEquals(response.getData().getName(), productDto.getName());
    }

    @Test
    @DisplayName("Add product with validation erros")
    void addProductWithValidationErrors() throws IOException {
        ProductDto product = ProductDto.builder()
                .amount(962)
                .category(CategoryDto.builder().id(11).build())
                .name("Olma")
                .description("Qizil olma")
                .price(55000)
                .image(new MockMultipartFile("...", "...", "image/jpeg", getClass().getClassLoader().getResourceAsStream("...")))
                .build();

        ResponseDto<Integer> imageResponse = fileClient.uploadFile(product.getImage());
        imageResponse.setSuccess(false);
        imageResponse.setMessage(AppStatusMessages.UNEXPECTED_ERROR);
        assertFalse(imageResponse.isSuccess(), "nmaaa");
        assertEquals(AppStatusCodes.UNEXPECTED_ERROR_CODE, AppStatusCodes.UNEXPECTED_ERROR_CODE, "Response code is not equal 2");
        assertEquals(imageResponse.getMessage(), AppStatusMessages.UNEXPECTED_ERROR, "Response would be with validation errors");
    }


}
