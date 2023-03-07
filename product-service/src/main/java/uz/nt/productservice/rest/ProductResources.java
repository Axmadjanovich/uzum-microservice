package uz.nt.productservice.rest;

import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
>>>>>>> develop
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
    public ResponseDto<ProductDto> addNewProduct(@RequestBody ProductDto productDto) {
        return productService.addNewProduct(productDto);
    }

    @PatchMapping
<<<<<<< HEAD
=======
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
>>>>>>> develop
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping()
<<<<<<< HEAD
    public ResponseDto<List<EntityModel<ProductDto>>> getAllProducts(
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "2") Integer size) {
=======
    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(@RequestParam(defaultValue = "10") Integer size,
                                                                     @RequestParam(defaultValue = "0") Integer page) {
>>>>>>> develop
        return productService.getAllProducts(page, size);
    }

    @GetMapping("by-id")
<<<<<<< HEAD
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
=======
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
>>>>>>> develop

}
