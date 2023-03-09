package uz.nt.productservice.service;


import dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import uz.nt.productservice.dto.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseDto<ProductDto> addNewProduct(ProductDto productDto) throws IOException;

    ResponseDto<ProductDto> updateProduct(ProductDto productDto);

    ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size);

    ResponseDto<ProductDto> getProductById(Integer id);

//    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);

    ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params);

    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);

//    ResponseDto<Page<ProductDto>> getExpensiveProducts();
}
