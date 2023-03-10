package uz.nt.productservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.models.Category;
import uz.nt.productservice.repository.CategoryRepository;
import uz.nt.productservice.service.CategoryService;
import uz.nt.productservice.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;



    @Override
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {

        return ResponseDto.<CategoryDto>builder()
                .data(categoryMapper.toDto(
                        categoryRepository.save(
                                categoryMapper.toEntity(categoryDto)
                        )
                ))
                .message("OK")
                .success(true)
                .build();    }

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
    public ResponseDto<CategoryDto> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseDto.<CategoryDto>builder()
                .message("OK")
                .data((CategoryDto) categoryList)
                .success(true)
                .build();
    }
}
