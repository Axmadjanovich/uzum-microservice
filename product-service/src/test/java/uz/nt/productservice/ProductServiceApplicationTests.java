package uz.nt.productservice;

import dto.ResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.exceptions.NegativeNumberException;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.service.impl.ProductServiceImpl;
import uz.nt.productservice.service.mapper.ProductMapper;
import uz.nt.productservice.service.validator.NumberService;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private NumberService numberService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

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

//    Tests for update product method

    @Test
    @DisplayName("Test for update product method checks id is null")
    void test_for_update_product_id_is_null(){
        ProductDto productDto = new ProductDto();
        productDto.setAmount(1200);
        productDto.setName("Anjir");
        productDto.setDescription("Shiring anjir");

        ResponseDto<ProductDto> response = productServiceImpl.updateProduct(productDto);

        assertAll(
                () -> assertEquals(response.getCode(),-2,"Must return -2 because id is null"),
                () -> assertFalse(response.isSuccess(),"Must be false because id is null")
        );
    }
    @Test
    @DisplayName("Checks id is exists in database")
    void check_product_id_is_exists(){
        ProductDto productDto = new ProductDto();
        productDto.setAmount(1200);
        productDto.setId(10);
        productDto.setName("Anjir");
        productDto.setDescription("Shiring anjir");

        ResponseDto<ProductDto> response = productServiceImpl.updateProduct(productDto);

        assertAll(
                () -> assertEquals(response.getCode(),-1,"Must return -1 because id is not exists in db"),
                () -> assertFalse(response.isSuccess(),"Must be false because id is not exists")
        );

    }

    @Test
    @DisplayName("checks product is  updated to db: ")
    void check_data_is_updated(){
        ProductDto productDto = new ProductDto();
        productDto.setAmount(1);
        productDto.setId(1);
        productDto.setName("olma");
        productDto.setDescription("Shiring anjir");

        ResponseDto<ProductDto> response = productServiceImpl.updateProduct(productDto);
        assertAll(
                () -> assertEquals(response.getCode(),0,"Must return 0 because data must save successfully"),
                () -> assertTrue(response.isSuccess())
        );
    }

//    Test for get product by id method

    @Test
    @DisplayName("Test for get product by-id")
    void get_product_by_id(){
//        checks data that is  exists in db:
        ResponseDto<ProductDto> response = productServiceImpl.getProductById(1);
        assertAll(
                () -> assertEquals(response.getCode(), 0,"Must return 0: because product is exists"),
                () -> assertTrue(response.isSuccess(),"Must return true. Reason: data is exists")
        );

//        checks data that is not exists in db:
        ResponseDto<ProductDto> responseForNotFound = productServiceImpl.getProductById(100);
        assertAll(
                () -> assertEquals(responseForNotFound.getCode(), -1,"Must return -1: because product is not exists"),
                () -> assertFalse(responseForNotFound.isSuccess(),"Must return false. Reason: data is not exists")
        );

    }

//    Test for search

    @Test
    @DisplayName("Test for universalSearch method")
    void check_params_are_exists_or_not(){
        Map<String ,String > params = new HashMap<>();
        params.put("name","kiwi");
        params.put("size","10");
        params.put("page","1");

        ResponseDto<Page<ProductDto>> response = productServiceImpl.universalSearch2(params);

        assertAll(
                () -> assertEquals(response.getCode(),-1,"Must return -1. Reason: data is not found"),
                () -> assertFalse(response.isSuccess(),"Must be false. Because data is not found")
        );

        ResponseDto<Page<ProductDto>> responseDto = productServiceImpl.universalSearch2(params);

        assertAll(
                () -> assertEquals(responseDto.getCode(),-1,"Must return -1. Reason: data is not found"),
                () -> assertFalse(responseDto.isSuccess(),"Must be false. Because data is not found")
        );
    }
}
