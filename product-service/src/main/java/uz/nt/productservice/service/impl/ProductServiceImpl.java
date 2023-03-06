package uz.nt.productservice.service.impl;

import dto.ErrorDto;
import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
import uz.nt.productservice.service.validator.ProductValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
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
                    .code(-2)
                    .success(false)
                    .build();
        }

        Product product = productMapper.toEntity(productDto);

        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .success(true)
                .code(0)
                .data(productMapper.toDto(product))
                .message("OK")
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

        Optional<Product> optional = productRepository.findById(productDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .code(-1)
                    .message("NOT FOUND")
                    .build();
        }

        Product product = optional.get();

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
                    .code(0)
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message("DATABASE ERROR" + ": " + e.getMessage())
                    .code(-2)
                    .build();
        }
    }

    @Override
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
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;
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
                .data(products)
                .build();
    }
}
