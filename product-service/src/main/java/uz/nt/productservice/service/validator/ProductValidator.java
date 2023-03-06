package uz.nt.productservice.service.validator;

import dto.ErrorDto;
import org.springframework.stereotype.Component;
import uz.nt.productservice.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public List<ErrorDto> validateProduct(ProductDto productDto){
        List<ErrorDto> errors = new ArrayList<>();

        if (productDto.getAmount() == null){
            errors.add(new ErrorDto("amount", "Value is null"));
        } else if (productDto.getAmount() <= 0) {
            errors.add(new ErrorDto("amount", "Value must be greater than 0"));
        }
        if (productDto.getPrice() == null){
            errors.add(new ErrorDto("price", "Value is null"));
        } else if (productDto.getPrice() <= 0) {
            errors.add(new ErrorDto("price", "Value must be greater than 0"));
        }
        if (productDto.getDescription() == null){
            errors.add(new ErrorDto("description", "Value is null"));
        } else if (productDto.getDescription().trim().length() == 0) {
            errors.add(new ErrorDto("description", "Value is empty"));
        }
        if (productDto.getName() == null){
            errors.add(new ErrorDto("name", "Value is null"));
        }
        return errors;
    }
}
