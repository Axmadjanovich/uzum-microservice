package uz.nt.productservice.service;

import dto.ResponseDto;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.dto.ProductDto;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto);
    ResponseDto<CategoryDto> getAll();
//    ResponseDto<CategoryDto> deleteCategory(Integer id);
}
