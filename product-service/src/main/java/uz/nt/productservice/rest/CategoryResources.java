package uz.nt.productservice.rest;

import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.productservice.dto.CategoryDto;
import uz.nt.productservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryResources {
    private final CategoryService categoryService;
    @Operation(
            summary = "Add new Category",
            method = "Add new Category",
            description = "Need to send CategoryDto to this endpoint to create new category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Category info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping()
    public ResponseDto<CategoryDto> addNewCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @Operation(
            summary = "Update Category",
            method = "Update Category",
            description = "Need to send CategoryDto to this endpoint to category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Category info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Category not found")}
    )
    @PatchMapping()
    public ResponseDto<CategoryDto> editCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }


    @Operation(
            summary = "Get all Categories",
            method = "Get Category",
            description = "Get all categories",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Category info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Category not found")}
    )
    @GetMapping()
    public ResponseDto<List<CategoryDto>> viewAllCategory(){
        return categoryService.getAll();
    }



    @Operation(
            summary = "Get Category by id",
            method = "Get Category by id",
            description = "Get category by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "category info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Category not found")}
    )
    @GetMapping("by-id")
    public ResponseDto<CategoryDto> viewById(@RequestParam Integer id){
        return categoryService.getById(id);
    }



    @Operation(
            summary = "Delete",
            method = "Delete category",
            description = "Delete category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Category info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Category not found")}
    )
    @DeleteMapping()
    public ResponseDto<CategoryDto> removeCategory(@RequestParam Integer id){
        return categoryService.deleteCategory(id);
    }

}
