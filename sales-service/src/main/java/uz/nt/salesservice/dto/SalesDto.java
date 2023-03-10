package uz.nt.salesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    @NotNull
    private Date expressionDate;
}
