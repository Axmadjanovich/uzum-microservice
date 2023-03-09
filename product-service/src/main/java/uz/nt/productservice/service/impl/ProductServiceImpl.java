package uz.nt.productservice.service.impl;

import dto.ErrorDto;
import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import uz.nt.productservice.clients.FileClient;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.repository.ProductRepositoryImpl;
import uz.nt.productservice.rest.ProductResources;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.mapper.ProductMapper;
import uz.nt.productservice.service.validator.ValidationService;
import validator.AppStatusCodes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepositoryImpl;

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final FileClient fileClient;

    @Override
    public ResponseDto<ProductDto> addNewProduct(ProductDto productDto) throws IOException {

        List<ErrorDto> errors = ValidationService.validation(productDto);

        if (!errors.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .errors(errors)
                    .data(productDto)
                    .message("VALIDATION_ERROR")
                    .code(-2)
                    .success(false)
                    .build();
        }

        Product product = productMapper.toEntity(productDto);

        ResponseDto<Integer> imageResponse = fileClient.uploadFile(productDto.getImage());
        if (!imageResponse.isSuccess()){
            return ResponseDto.<ProductDto>builder()
                    .message(imageResponse.getMessage())
                    .code(AppStatusCodes.UNEXPECTED_ERROR_CODE)
                    .build();
        }

        product.setFileId(imageResponse.getData());
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .data(productMapper.toDto(product))
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {

        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(-2)
                    .build();
        }

        Optional<Product> productOptional = productRepository.findById(productDto.getId());
        if (productOptional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .message("NOT_FOUND")
                    .code(-1) //NOT_FOUND_ERROR_CODE
                    .data(productDto)
                    .build();
        }

        Product product = productOptional.get();

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null && productDto.getPrice() > 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null && productDto.getAmount() > 0) {
            product.setIsAvailable(true);
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }

        try {

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message("OK")
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message("DATABASE_ERROR" + ": " + e.getMessage())
                    .code(2) //DATABASE_ERROR_CODE
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size) {
        Long count = productRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size <= page) ? ((count % size == 0) ? (int) (count / size) - 1 : (int) (count / size)) : page, size
        );

        Page<EntityModel<ProductDto>> products = productRepository.findAll(pageRequest)
                .map(p -> {
                    EntityModel entityModel = EntityModel.of(p);
                    try {
                        entityModel.add(linkTo(ProductResources.class.getDeclaredMethod("getProductById", Integer.class, HttpServletRequest.class))
                                .withSelfRel()
                                .expand(p.getId())
                        );
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;
                });

        return ResponseDto.<Page<EntityModel<ProductDto>>>builder()
                .data(products)
                .message("OK")
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(products))
                        .success(true)
                        .code(0)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message("NOT_FOUND")
                        .code(-1) // NOT_FOUND_ERROR_CODE
                        .build()
                );
    }

    @Override
    public ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params) {
        Page<Product> products = productRepositoryImpl.universalSearch2(params);

        if (products.isEmpty()){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-1) //NOT_FOUND_ERROR_CODE
                    .message("NOT_FOUND")
                    .build();
        }

        return ResponseDto.<Page<ProductDto>>builder()
                .message("OK")
                .data(products.map(productMapper::toDto))
                .build();


    }

    @Override
    public ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort) {
        List<Sort.Order> orderList = sort.stream().map(s -> new Sort.Order(Sort.Direction.DESC,s)).toList();

        List<ProductDto> products = productRepository.findAll(Sort.by(orderList))
                .stream()
                .map(productMapper::toDto).toList();

        return ResponseDto.<List<ProductDto>>builder()
                .data(products)
                .success(true)
                .message("OK")
                .build();

    }

//    @Override
//    public ResponseDto<Page<ProductDto>> getExpensiveProducts() {
//        Page<ProductDto> products = productRepository.getExpensiveProducts2().map(productMapper::toDto);
//
//
//        return ResponseDto.<Page<ProductDto>>builder()
//                .data(products)
//                .message("OK")
//                .success(true)
//                .build();
//    }

//    @Override
//    public ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort) {
//        return null;
//    }
}
