package uz.nt.productservice.service.impl;

import dto.ErrorDto;
import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
>>>>>>> develop
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.repository.ProductRepositoryImpl;
import uz.nt.productservice.rest.ProductResources;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.mapper.ProductMapper;
<<<<<<< HEAD
import uz.nt.productservice.service.validator.ProductValidator;
=======
import uz.nt.productservice.service.validator.ValidationService;
>>>>>>> develop

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepositoryImpl;

    private final ProductMapper productMapper;
<<<<<<< HEAD
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ProductValidator productValidator;

    @Override
    public ResponseDto<ProductDto> addNewProduct(ProductDto productDto) {
        List<ErrorDto> errors = productValidator.validateProduct(productDto);

        if (!errors.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .errors(errors)
                    .data(productDto)
                    .message("Validation error")
=======
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<ProductDto> addNewProduct(ProductDto productDto) {

        List<ErrorDto> errors = ValidationService.validation(productDto);

        if (!errors.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .errors(errors)
                    .data(productDto)
                    .message("VALIDATION_ERROR")
>>>>>>> develop
                    .code(-2)
                    .success(false)
                    .build();
        }

<<<<<<< HEAD
        Product product = productMapper.toEntity(productDto);

        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .success(true)
                .code(0)
                .data(productMapper.toDto(product))
                .message("OK")
=======

        return ResponseDto.<ProductDto>builder()
                .data(productMapper.toDto(productRepository.save(productMapper.toEntity(productDto))))
                .success(true)
                .message("Ok")
>>>>>>> develop
                .build();
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
<<<<<<< HEAD
=======

>>>>>>> develop
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(-2)
                    .build();
        }

<<<<<<< HEAD
        Optional<Product> optional = productRepository.findById(productDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .code(-1)
                    .message("NOT FOUND")
                    .build();
        }

        Product product = optional.get();
=======
        Optional<Product> productOptional = productRepository.findById(productDto.getId());
        if (productOptional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .message("NOT_FOUND")
                    .code(-1) //NOT_FOUND_ERROR_CODE
                    .data(productDto)
                    .build();
        }

        Product product = productOptional.get();
>>>>>>> develop

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
<<<<<<< HEAD
                    .code(0)
=======
>>>>>>> develop
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
<<<<<<< HEAD
                    .message("DATABASE ERROR" + ": " + e.getMessage())
                    .code(-2)
=======
                    .message("DATABASE_ERROR" + ": " + e.getMessage())
                    .code(2) //DATABASE_ERROR_CODE
>>>>>>> develop
                    .build();
        }
    }

    @Override
<<<<<<< HEAD
    public ResponseDto<List<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size) {
        Long count = productRepository.count();
        List<EntityModel<ProductDto>> products = productRepository.findAll(PageRequest.of((count / size) < page ?
                        (int) (count % size == 0 ? (count / size - 1) : (count / size)) : page, size))
                .map(p -> {
                    EntityModel<ProductDto> entityModel = EntityModel.of(productMapper.toDto(p));
                    try {
                        entityModel.add(linkTo(ProductResources.class
                                .getDeclaredMethod("getProductById", Integer.class, HttpServletRequest.class))
                                .withSelfRel()
                                .expand(p.getId()));
=======
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
>>>>>>> develop
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;
<<<<<<< HEAD
                })
                .toList();

        return ResponseDto.<List<EntityModel<ProductDto>>>builder()
                .message("OK")
                .code(0)
                .success(true)
                .data(products)
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
                        .message("NOT FOUND")
                        .code(-1)
                        .build()
                );
    }

    @Override
    public ResponseDto<List<ProductDto>> getExpensiveProducts() {
        List<ProductDto> products = productRepository.getExpensiveProducts2().stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseDto.<List<ProductDto>>builder()
                .message("OK")
                .code(0)
                .success(true)
                .data(products)
                .build();
    }

    @Override
    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto) {
        List<Product> products = productRepository.findProductById(productDto.getId(), productDto.getName(), productDto.getAmount(), productDto.getPrice(), productDto.getDescription(), productDto.getCategoryId());
        if (products.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .code(-1)
                    .message("NOT FOUND")
                    .build();
        }

        return ResponseDto.<List<ProductDto>>builder()
                .message("OK")
                .code(0)
                .data(products.stream().map(productMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDto<List<ProductDto>> universalSearch2(Map<String, String> params) {
        List<Product> products = productRepositoryImpl.universalSearch(params);

        if (products.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .code(-1)
                    .message("NOT FOUND")
                    .build();
        }

        return ResponseDto.<List<ProductDto>>builder()
=======
                });

        return ResponseDto.<Page<EntityModel<ProductDto>>>builder()
                .data(products)
>>>>>>> develop
                .message("OK")
                .code(0)
                .data(products.stream().map(productMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort) {
        List<Sort.Order> orders = sort.stream()
                .map(s -> new Sort.Order(Sort.Direction.DESC, s))
                .toList();

        List<ProductDto> products = productRepository.findAll(Sort.by(orders)).stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseDto.<List<ProductDto>>builder()
                .message("OK")
                .code(0)
                .success(true)
<<<<<<< HEAD
                .data(products)
=======
>>>>>>> develop
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
