package uz.nt.salesservice.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.CONSTRUCTOR})
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface IsValidDate {
    String message() default "Is not valid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
