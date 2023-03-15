package uz.nt.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private Integer id;
    private Integer productId;
    private Double price;
    private LocalDateTime expirationDate;
}
