package uz.nt.productservice.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

@Mapper(componentModel = "spring")
//@RequiredArgsConstructor
public abstract class ProductMapper implements CommonMapper<ProductDto, Product>{

    @Autowired
    protected CategoryMapper categoryMapper;

    @Autowired
    protected UnitMapper unitMapper;

    @Mapping(target = "category", expression = "java(categoryMapper.toDto(product.getCategory()))")
    @Mapping(target = "units", expression = "java(unitMapper.toDto(product.getUnits()))")
    public abstract ProductDto toDto(Product product);

    @Mapping(target = "isAvailable", expression = "java( productDto.getAmount() != null && productDto.getAmount() > 0)")
    public abstract Product toEntity(ProductDto productDto);
}
