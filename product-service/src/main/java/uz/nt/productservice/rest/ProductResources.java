package uz.nt.productservice.rest;

import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping()
    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(@RequestParam(defaultValue = "10") Integer size,
                                                                     @RequestParam(defaultValue = "0") Integer page) {
        return productService.getAllProducts(page, size);
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
