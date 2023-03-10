package uz.nt.salesservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.salesservice.service.validator.IsValidDate;

import static uz.nt.salesservice.service.validator.AppStatusMessages.EMPTY_STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesDto {

    private Integer id;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer price;

    private String expressionDate;
}
