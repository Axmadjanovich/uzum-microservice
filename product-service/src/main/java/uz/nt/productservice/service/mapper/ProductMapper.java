package uz.nt.productservice.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class ProductMapper {

    public abstract ProductDto toDto(Product product);

    @Mapping(target = "isAvailable", expression = "java( productDto.getAmount() != null && productDto.getAmount() > 0)")
    public abstract Product toEntity(ProductDto productDto);
}
