package uz.nt.productservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.repository.CategoryRepository;
import uz.nt.productservice.service.CategoryService;
import uz.nt.productservice.service.mapper.CategoryMapper;

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
}
