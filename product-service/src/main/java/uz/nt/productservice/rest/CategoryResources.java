package uz.nt.productservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.service.CategoryService;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryResources {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseDto<CategoryDto> addNewCategory(@ModelAttribute CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

}
