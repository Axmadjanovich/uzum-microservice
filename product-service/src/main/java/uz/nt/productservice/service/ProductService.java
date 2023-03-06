package uz.nt.productservice.service;

import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.productservice.dto.ProductDto;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseDto<ProductDto> addNewProduct(ProductDto productDto);
    ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    ResponseDto<List<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size);
    ResponseDto<ProductDto> getProductById(Integer id);
    ResponseDto<List<ProductDto>> getExpensiveProducts();
    ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto);
    ResponseDto<List<ProductDto>> universalSearch2(Map<String, String> params);
    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);



}
