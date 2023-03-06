package uz.nt.productservice.rest;

import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    public ResponseDto<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        return productService.addNewProduct(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping()
    public ResponseDto<List<EntityModel<ProductDto>>> getAllProducts(
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "2") Integer size) {
        return productService.getAllProducts(page, size);
    }

    @GetMapping("by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id, HttpServletRequest req){
        String authorization = req.getHeader("Authorization");
        return productService.getProductById(id);
    }

    @GetMapping("/expensive-by-category")
    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
        return productService.getExpensiveProducts();
    }

    @GetMapping("search")
    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto){
        return productService.universalSearch(productDto);
    }

    @GetMapping("search-2")
    public ResponseDto<List<ProductDto>> universalSearch2(@RequestParam Map<String, String> params){
        return productService.universalSearch2(params);
    }

    @GetMapping("get-sort")
    public ResponseDto<List<ProductDto>> getAllProductWithSort(@RequestParam List<String> sort) {
        return productService.getAllProductsWithSort(sort);
    }

}
