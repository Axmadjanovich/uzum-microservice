package uz.nt.productservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.service.ProductService;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    public ResponseDto<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        return productService.addNewProduct(productDto);
    }
}
