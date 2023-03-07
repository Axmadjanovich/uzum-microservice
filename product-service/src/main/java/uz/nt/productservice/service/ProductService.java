package uz.nt.productservice.service;


import dto.ResponseDto;
<<<<<<< HEAD
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
=======
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
>>>>>>> develop
import uz.nt.productservice.dto.ProductDto;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseDto<ProductDto> addNewProduct(ProductDto productDto);
<<<<<<< HEAD
    ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    ResponseDto<List<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size);
    ResponseDto<ProductDto> getProductById(Integer id);
    ResponseDto<List<ProductDto>> getExpensiveProducts();
    ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto);
    ResponseDto<List<ProductDto>> universalSearch2(Map<String, String> params);
    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);



=======

    ResponseDto<ProductDto> updateProduct(ProductDto productDto);

    ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size);

    ResponseDto<ProductDto> getProductById(Integer id);

//    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);

    ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params);

    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);

//    ResponseDto<Page<ProductDto>> getExpensiveProducts();
>>>>>>> develop
}
