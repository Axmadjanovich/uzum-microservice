package uz.nt.productservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.dto.UnitDto;
import uz.nt.productservice.models.Category;
import uz.nt.productservice.repository.CategoryRepository;
import uz.nt.productservice.service.CategoryService;
import uz.nt.productservice.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;

import static validator.AppStatusCodes.OK_CODE;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;



    @Override
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message("OK")
                    .success(true)
                    .build();

        }catch (Exception e ){
            return ResponseDto.<CategoryDto>builder()
                    .code(-1)
                    .message("DATABASE_ERROR" + ": " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() == null){
            return ResponseDto.<CategoryDto>builder()
                    .code(-2)
                    .message("Category id is null")
                    .build();
        }
        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());
        if (categoryOptional.isEmpty()){
            return ResponseDto.<CategoryDto>builder()
                    .message("NOT_FOUND")
                    .code(-1)
                    .data(categoryDto)
                    .build();
        }

        Category category = categoryOptional.get();
        if (categoryDto.getName() != null){
            category.setName(categoryDto.getName());
        }
        if (categoryDto.getParentId() != null) {
            category.setParentId(categoryDto.getParentId());
        }
        categoryRepository.save(category);

        return ResponseDto.<CategoryDto>builder()
                .message("OK")
                .success(true)
                .data(categoryMapper.toDto(category))
                .build();
    }

    @Override
    public ResponseDto<List<CategoryDto>> getAll() {
        List<CategoryDto> categoryList = categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();

        if (categoryList.size() > 1) {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message("OK")
                    .data(categoryList)
                    .success(true)
                    .build();
        }
        return ResponseDto.<List<CategoryDto>>builder()
                .message("Categories are not found!")
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<CategoryDto> getById(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> ResponseDto.<CategoryDto>builder()
                        .data(categoryMapper.toDto(category))
                        .success(true)
                        .code(0)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<CategoryDto>builder()
                        .message("NOT_FOUND")
                        .code(-1)
                        .build()
                );
    }

    @Override
    public ResponseDto<CategoryDto> deleteCategory(Integer id) {
        Optional<Category> categoryById = categoryRepository.findById(id);

        if (categoryById.isPresent()) {
            Category category = categoryById.get();
            categoryRepository.deleteById(id);
            return ResponseDto.<CategoryDto>builder()
                    .message("Category " + id + " has been deleted")
                    .code(1)
                    .success(true)
                    .data(categoryMapper.toDto(category))
                    .build();
        }
        return ResponseDto.<CategoryDto>builder()
                .message("Category " + id + " is not available")
                .success(false)
                .code(-1)
                .build();
    }
}