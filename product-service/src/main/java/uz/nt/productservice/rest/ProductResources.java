package uz.nt.productservice.rest;

import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

//TODO Coders: miqdori 10 dan kam bo'lgan mahsulotlarni listini qaytarish

//TODO Coders: Units jadvalini qo'shish va har bir mahsulot uchun o'lchov birligini biriktirish.
// Units => id, shortName, name, description
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Slf4j
public class ProductResources {

    //TODO Coders: Category qo'shish

    private final ProductService productService;
    private final Environment environment;

    @Operation(
            summary = "Add new Product",
            method = "Add new Product",
            description = "Need to send ProductDto to this endpoint to create new product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public ResponseDto<ProductDto> addNewProduct(@ModelAttribute ProductDto productDto) throws IOException {
        return productService.addNewProduct(productDto);
    }

    @Operation(
            summary = "Update Product",
            method = "Update Product",
            description = "Need to send ProductDto to this endpoint to product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @PatchMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @Operation(
            summary = "Get all Products",
            method = "Get Products",
            description = "Get all products",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping()
    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(@RequestParam(defaultValue = "10") Integer size,
                                                                     @RequestParam(defaultValue = "0") Integer page) {
        ResponseDto<Page<EntityModel<ProductDto>>> allProducts = productService.getAllProducts(page, size);
        allProducts.setMessage("Message from " + environment.getProperty("server.port") + " " + allProducts.getMessage());
        return allProducts;
    }

    @Operation(
            summary = "Get Product by id",
            method = "Get Products by id",
            description = "Get product by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping("by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id, HttpServletRequest req) {
        return productService.getProductById(id);
    }

    @Operation(
            summary = "Universal search",
            method = "Universal search",
            description = "Universal search",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping("search-2")
    public ResponseDto<Page<ProductDto>> universalSearch(@RequestParam Map<String, String> params) {
        return productService.universalSearch2(params);
    }

    @Operation(
            summary = "Get a sorted list of products",
            method = "Sort products",
            description = "Sort",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping("sort")
    public ResponseDto<List<ProductDto>> getProducts(@RequestParam List<String> sort) {
        return productService.getAllProductsWithSort(sort);
    }

    @Operation(
            summary = "Get expensive products by category",
            method = "get expensive products",
            description = "get expensive products",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping("/expensive-by-category")
    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
        return productService.getExpensiveProducts();
    }

    @Operation(
            summary = "Get products for sales",
            method = "For sales",
            description = "Get products for sales",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping("for-sales")
    public ResponseDto<Stream<ProductDto>> getProductsForSales(@RequestParam List<Integer> salesList){
//        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13);
        return productService.getProductsForSales(salesList);
    }

    @Operation(
            summary = "Get all products with less amount",
            method = "product with less amount",
            description = "Get all products with less amount",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Product not found")}
    )
    @GetMapping("product-with-less-amount")
    public ResponseDto<Stream<ProductDto>> getAllProductsWithLessAmount(){
        return productService.getAllProductsWithLessAmount();
    }

}
