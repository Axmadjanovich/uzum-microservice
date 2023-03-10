package uz.nt.userservice.rest;

import dto.ProductDto;
import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import uz.nt.userservice.client.ProductClient;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.service.impl.UsersServiceImpl;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersServiceImpl usersService;
    private final ProductClient productClient;
    //TODO AppMonsters: ValidationError uchun ExceptionHandler yozish
    @Operation(
            method = "Add new User",
            description = "Need to send UsersDto to this endpoint to create new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info", content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Authorization error")}
    )
    @PostMapping()
    public ResponseDto<UsersDto> addUser(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @GetMapping("by-phone-number")
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return usersService.getById(id);
    }

    @GetMapping("/products")
    public ResponseDto<Page<EntityModel<ProductDto>>> getProductList(){
        return productClient.getProducts(10, 0);
    }
}
