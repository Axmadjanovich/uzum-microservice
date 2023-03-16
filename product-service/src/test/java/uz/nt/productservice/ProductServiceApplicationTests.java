package uz.nt.productservice;

import dto.ResponseDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.exception.NegativeNumberException;
import uz.nt.productservice.models.Category;
import uz.nt.productservice.models.Product;
import uz.nt.productservice.service.NumberService;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.impl.CategoryServiceImpl;
import uz.nt.productservice.service.impl.ProductServiceImpl;
import validator.AppStatusMessages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private NumberService numberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductServiceImpl productServiceImpl;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

//    @Test
//    @DisplayName("calculate two numbers when they are equal")
//    void calculate() {
//        Integer result = numberService.calculate(2, 2);
//        assertEquals(result, 0, "Add two nums method works incorrectly");
//    }
//
//    @Test
//    void calculate_two_number_when_first_number_is_greater_than_second(){
//        Integer result = numberService.calculate(11, 4);
//
//        assertEquals(result, 11/4, "Numbers must be divided");
//    }
//
//    @Test
//    void calculate_two_numbers_when_first_number_is_greater_and_negative(){
//        Assertions.assertThrows(NumberFormatException.class, () -> numberService.calculate(0, -12));
//        Assertions.assertThrowsExactly(NegativeNumberException.class, ()-> numberService.calculate(0, -12),"Number");
//    }
//
//    @Test
//    @DisplayName("Add product with validation errors")
//    void addProductWithValidationError() throws IOException {
//        ProductDto product = new ProductDto();
//        product.setAmount(100);
//        product.setName("Xurmo");
//
//        ResponseDto<ProductDto> response = productService.addNewProduct(product);
//        assertEquals(response.getCode(), -2, "Response code -2");
//        assertEquals(response.getMessage(), AppStatusMessages.VALIDATION_ERROR, "Response would be validation error");
//        assertFalse(response.isSuccess(), "Response would be unsuccessful");
//
//        assertEquals(response.getErrors().size(), 2);
//    }

//    @Test
//    @Sql(executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/insert-test-values.sql")
//    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-values.sql")
//    @DisplayName("Add product with valid value")
//    void addProduct() throws IOException {
//        ProductDto productDto = ProductDto.builder()
//                .amount(100)
//                .category(CategoryDto.builder().id(1).build())
//                .name("Xurmo")
//                .description("Misr xurmosi")
//                .price(55000)
//                .image(new MockMultipartFile("xurmo.jgp","xurmo.jpg" ,"image/jpeg", getClass().getClassLoader().getResourceAsStream("xurmo.jpeg")))
//                .build();
//
//        ResponseDto<ProductDto> responseDto = productService.addNewProduct(productDto);
//
//        Assertions.assertTrue(responseDto.isSuccess());
//        Assertions.assertNotNull(responseDto.getData());
//        Assertions.assertNotNull(responseDto.getData().getId());
//        assertEquals(responseDto.getData().getName(), productDto.getName());
//    }



    @Test
    @DisplayName("Add category")
    void addCategory(){
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Texnika")
                .parentId(4)
                .build();

        ResponseDto<CategoryDto> categoryDtoResponseDto = categoryServiceImpl.addCategory(categoryDto);
        assertTrue(categoryDtoResponseDto.isSuccess());
        assertNotNull(categoryDtoResponseDto.getData(), "Category data must be available");
        assertNotNull(categoryDtoResponseDto.getData().getId(), "Category id must be available");
        assertEquals(categoryDtoResponseDto.getData().getName(), categoryDto.getName());
    }
    @Test
    @DisplayName("Update category")
    void editCategory(){
        CategoryDto categoryDto = new CategoryDto();
//        categoryDto.setId(3);
        categoryDto.setName("Gazli ichimlik");
        categoryDto.setParentId(3);

        ResponseDto<CategoryDto> categoryDtoResponseDto = categoryServiceImpl.updateCategory(categoryDto);
        assertAll(
                () -> assertEquals(categoryDtoResponseDto.getCode(), -2),
                () -> assertFalse(categoryDtoResponseDto.isSuccess())
        );

    }

    @Test
    @DisplayName("View all categories")
    void getAllCategory(){
        ResponseDto<List<CategoryDto>> allCategories = categoryServiceImpl.getAll();
        assertAll(
                () ->       assertEquals(allCategories.getData().size()>1, true),
                () ->       assertTrue(allCategories.isSuccess()),
                () ->       assertEquals(allCategories.getMessage(), AppStatusMessages.OK)
        );

//        ResponseDto<List<CategoryDto>> all = categoryServiceImpl.getAll();
//            assertAll(
//                    () -> assertTrue(all.isSuccess()),
//                    () -> assertEquals(all.getMessage(), AppStatusMessages.NULL_VALUE," Category is unavailable")
//            );
    }

    @Test
    @Sql(executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/insert-test-values.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-values.sql")
    @DisplayName("View category by id")
    void getCategoryById(){
        ResponseDto<CategoryDto> byId = categoryServiceImpl.getById(12);
        assertAll(
                () -> assertNotNull(byId.getData().getId()),
                () -> assertEquals(byId.getMessage(),AppStatusMessages.OK),
                () -> assertTrue(byId.isSuccess()),
                () -> assertEquals(byId.getCode(), 0)
        );
    }

    @Test
    @DisplayName("Delete category by id")
    void deleteCategory(){
        ResponseDto<CategoryDto> response = categoryServiceImpl.deleteCategory(2);

        assertAll(
                () -> assertNotNull(response.getData().getId()),
                () -> assertEquals(response.getCode(), 1),
                () -> assertEquals(response.getMessage(), AppStatusMessages.OK),
                () -> assertTrue(response.isSuccess())
        );
    }
}
