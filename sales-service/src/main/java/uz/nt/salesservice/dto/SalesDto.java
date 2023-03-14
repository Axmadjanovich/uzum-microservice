package uz.nt.salesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @NotNull(message = "Example: yyyy-MM-dd HH:mm:ss")
    private String expirationDate;
}
