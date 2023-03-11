package uz.nt.productservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.service.CategoryService;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryResources {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseDto<CategoryDto> addNewCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @PatchMapping()
    public ResponseDto<CategoryDto> editCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }
    @GetMapping()
    public ResponseDto<CategoryDto> viewAllProducts(){
        return categoryService.getAll();
    }

}
