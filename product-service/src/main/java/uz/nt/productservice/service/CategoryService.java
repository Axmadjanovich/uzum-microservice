package uz.nt.productservice.service;

import dto.ResponseDto;
import uz.nt.productservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto);
    ResponseDto<List<CategoryDto>> getAll();
    ResponseDto<CategoryDto> getById(Integer id);
    ResponseDto<CategoryDto> deleteCategory(Integer id);
}
