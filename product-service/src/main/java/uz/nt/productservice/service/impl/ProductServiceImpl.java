package uz.nt.productservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.mapper.ProductMapper;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ResponseDto<ProductDto> addNewProduct(ProductDto productDto) {
        return ResponseDto.<ProductDto>builder()
                .code(0)
                .message("OK")
                .success(true)
                .data(productMapper.toDto(productRepository.save(productMapper.toEntity(productDto))))
                .build();
    }
}
