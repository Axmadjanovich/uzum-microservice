package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}
