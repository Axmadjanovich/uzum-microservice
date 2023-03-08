package uz.nt.productservice.service.validator;


import dto.ErrorDto;
import uz.nt.productservice.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class ValidationService {

    public static List<ErrorDto> validation(ProductDto dto){
        List<ErrorDto> errors = new ArrayList<>();

        if(dto.getAmount() == null){
            errors.add(new ErrorDto("amount","NULL_VALUE")); //NULL_VALUE
        } else if(dto.getAmount() < 0){
            errors.add(new ErrorDto("amount","NEGATIVE_VALUE")); //NEGATIVE_VALUE
        }

        if(dto.getName() == null){
            errors.add(new ErrorDto("name","NULL_VALUE")); // NULL_VALUE
        }else if(dto.getName().trim().length() == 0){
            errors.add(new ErrorDto("name","EMPTY_STRING")); //EMPTY_STRING
        }

        if(dto.getPrice() == null){
            errors.add(new ErrorDto("price", "NULL_VALUE")); //NULL_VALUE
        } else if(dto.getPrice() < 0){
            errors.add(new ErrorDto("price","NEGATIVE_VALUE" )); //NEGATIVE_VALUE
        }

        if(dto.getDescription() == null){
            errors.add(new ErrorDto("description","NULL_VALUE"));
        }else if (dto.getDescription().trim().length() == 0){
            errors.add(new ErrorDto("description","EMPTY_STRING"));
        }

        return errors;
    }
}

