package uz.nt.productservice.service;

import dto.ResponseDto;
import uz.nt.productservice.dto.ProductDto;

public interface ProductService {
    ResponseDto<ProductDto> addNewProduct(ProductDto productDto);
}
