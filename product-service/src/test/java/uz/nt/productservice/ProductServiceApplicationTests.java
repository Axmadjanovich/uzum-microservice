package uz.nt.productservice;

import dto.ResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.dto.UnitDto;
import uz.nt.productservice.models.Product;
import uz.nt.productservice.repository.ProductRepositoryImpl;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.UnitService;
import uz.nt.productservice.service.impl.CategoryServiceImpl;
import uz.nt.productservice.service.impl.ProductServiceImpl;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static validator.AppStatusCodes.*;
import static validator.AppStatusMessages.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    ProductRepositoryImpl productRepositoryImpl;

    @Autowired
    private UnitService unitService;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/insert-test-values.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-values.sql")
    @DisplayName("Add product with valid value")
    void addProduct() throws IOException {
        ProductDto productDto = ProductDto.builder()
                .amount(962)
                .category(CategoryDto.builder().id(1).build())
                .name("Olma")
                .description("Qizil olma")
                .price(19000)
                .image(new MockMultipartFile("olma.jpg", "olma.jpg", "image/jpeg", getClass().getClassLoader().getResourceAsStream("olma.jpg")))
                .build();

        ResponseDto<ProductDto> response = productService.addNewProduct(productDto);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getId());
        assertEquals(response.getData().getName(), productDto.getName());
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


    @Test
    @DisplayName("Universal search, isEmpty error")
    void universalSearchIsEmptyError() {
        Map<String, String> params = new HashMap<>();
        Page<Product> products = productRepositoryImpl.universalSearch2(params);

        // Product will never be empty
        assertTrue(products.isEmpty(), "Empty parameter");
    }

    @Test
    @DisplayName("Get expensive products")
    @Transactional
    void getAllExpensiveProducts() {

        ResponseDto<List<ProductDto>> productDto = productService.getExpensiveProducts();

        assertEquals(productDto.getCode(), OK_CODE);
        assertEquals(productDto.getMessage(), OK);
    }

    @Test
    @DisplayName("Get all products")
    void getAllProducts() {

        ResponseDto<Page<EntityModel<ProductDto>>> productDto = productService.getAllProducts(0, 100);

        assertEquals(productDto.getCode(), OK_CODE);
        assertEquals(productDto.getMessage(), OK);
    }

    @Test
    @DisplayName("Get all products with sort")
    void getAllProductsWithSort() {
        List<String> sort = new ArrayList<>();
        sort.add("price");
        ResponseDto<List<ProductDto>> productDto = productService.getAllProductsWithSort(sort);

        assertEquals(productDto.getCode(), OK_CODE);
        assertEquals(productDto.getMessage(), OK);
    }

    @Test
    @DisplayName("Get all units")
    void getAllUnits(){
        ResponseDto<List<UnitDto>> listResponseDto = unitService.getAllUnits();

        assertEquals(listResponseDto.getCode(), OK_CODE);
        assertEquals(listResponseDto.getMessage(), OK);
        assertTrue(listResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Test for add new unit")
    void addNewUnit(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.addNewUnit(
                UnitDto.builder()
                        .name("kilogramm")
                        .shortName("kg")
                        .description("Og'irlik o'lchov birligi")
                        .build()
        );

        assertEquals(unitDtoResponseDto.getMessage(), OK);
        assertEquals(unitDtoResponseDto.getCode(), OK_CODE);
        assertTrue(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Update unit with id null")
    void updateUnitIdNull(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.updateUnit(
                UnitDto.builder()
                        .id(null)
                        .name("metr")
                        .shortName("m")
                        .description("Uzunlik o'lchov birligi")
                        .build()
        );

        assertEquals(unitDtoResponseDto.getMessage(), NULL_VALUE);
        assertEquals(unitDtoResponseDto.getCode(), VALIDATION_ERROR_CODE);
        assertFalse(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Update unit not found")
    void updateUnitNotFound(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.updateUnit(
                UnitDto.builder()
                        .id(10000)
                        .name("metr")
                        .shortName("m")
                        .description("Uzunlik o'lchov birligi")
                        .build()
        );

        assertEquals(unitDtoResponseDto.getMessage(), NOT_FOUND);
        assertEquals(unitDtoResponseDto.getCode(), NOT_FOUND_ERROR_CODE);
        assertFalse(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Update unit with name null")
    void updateUnitNameNull(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.updateUnit(
                UnitDto.builder()
                        .id(1)
                        .name(null)
                        .shortName("m")
                        .description("Uzunlik o'lchov birligi")
                        .build()
        );

        assertEquals(unitDtoResponseDto.getMessage(), OK);
        assertEquals(unitDtoResponseDto.getCode(), OK_CODE);
        assertTrue(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Update unit with shortname null")
    void updateUnitShorNameNull(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.updateUnit(
                UnitDto.builder()
                        .id(1)
                        .name("metr")
                        .shortName(null)
                        .description("Uzunlik o'lchov birligi")
                        .build()
        );

        assertEquals(unitDtoResponseDto.getMessage(), OK);
        assertEquals(unitDtoResponseDto.getCode(), OK_CODE);
        assertTrue(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Update unit with description null")
    void updateUnitDescriptionNull(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.updateUnit(
                UnitDto.builder()
                        .id(1)
                        .name("metr")
                        .shortName("m")
                        .description(null)
                        .build()
        );

        assertEquals(unitDtoResponseDto.getMessage(), OK);
        assertEquals(unitDtoResponseDto.getCode(), OK_CODE);
        assertTrue(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Delete unit by id valid test")
    void deleteUnitByIdValid(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.deleteUnit(1);

        assertEquals(unitDtoResponseDto.getMessage(), OK);
        assertEquals(unitDtoResponseDto.getCode(), OK_CODE);
        assertTrue(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Delete unit by id not found test")
    void deleteUnitByIdNotFound(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.deleteUnit(1000);

        assertEquals(unitDtoResponseDto.getMessage(), NOT_FOUND);
        assertEquals(unitDtoResponseDto.getCode(), NOT_FOUND_ERROR_CODE);
        assertFalse(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Get unit by id valid test")
    void getUnitById(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.getById(1);

        assertEquals(unitDtoResponseDto.getMessage(), OK);
        assertEquals(unitDtoResponseDto.getCode(), OK_CODE);
        assertTrue(unitDtoResponseDto.isSuccess());
    }

    @Test
    @DisplayName("Get unit by id not found")
    void getUnitByIdNotFound(){
        ResponseDto<UnitDto> unitDtoResponseDto = unitService.getById(1000);

        assertEquals(unitDtoResponseDto.getMessage(), NOT_FOUND);
        assertEquals(unitDtoResponseDto.getCode(), NOT_FOUND_ERROR_CODE);
        assertFalse(unitDtoResponseDto.isSuccess());
    }

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
                () ->       assertTrue(allCategories.getData().size() > 1),
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
