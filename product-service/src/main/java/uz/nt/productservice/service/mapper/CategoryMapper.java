package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends CommonMapper<CategoryDto, Category> {
}
