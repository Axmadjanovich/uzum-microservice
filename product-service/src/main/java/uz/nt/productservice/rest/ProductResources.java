package uz.nt.productservice.rest;

import dto.ResponseDto;
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

    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public ResponseDto<ProductDto> addNewProduct(@ModelAttribute ProductDto productDto) throws IOException {
        return productService.addNewProduct(productDto);
    }

    @PatchMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping()
    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(@RequestParam(defaultValue = "10") Integer size,
                                                                     @RequestParam(defaultValue = "0") Integer page) {
        ResponseDto<Page<EntityModel<ProductDto>>> allProducts = productService.getAllProducts(page, size);
        allProducts.setMessage("Message from " + environment.getProperty("server.port") + " " + allProducts.getMessage());
        return allProducts;
    }

    @GetMapping("by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id, HttpServletRequest req) {
        return productService.getProductById(id);
    }

    @GetMapping("search-2")
    public ResponseDto<Page<ProductDto>> universalSearch(@RequestParam Map<String, String> params) {
        return productService.universalSearch2(params);
    }

    @GetMapping("sort")
    public ResponseDto<List<ProductDto>> getProducts(@RequestParam List<String> sort) {
        return productService.getAllProductsWithSort(sort);
    }
//    @GetMapping("/expensive-by-category")
//    public ResponseDto<Page<ProductDto>> getExpensiveProducts(){
//        return productService.getExpensiveProducts();
//    }



//
//    @GetMapping("sort")
//    public ResponseDto<List<ProductDto>> getProducts(@RequestParam List<String> sort){
//        return productService.getAllProductsWithSort(sort);
//    }

}
