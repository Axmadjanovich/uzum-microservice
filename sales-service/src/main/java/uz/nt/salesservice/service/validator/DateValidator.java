package uz.nt.salesservice.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<IsValidDate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
