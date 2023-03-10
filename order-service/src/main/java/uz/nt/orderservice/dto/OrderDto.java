package uz.nt.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Integer id;
    private Integer userId;
    private boolean payed;
    private LocalDateTime createdAt;
    private LocalDateTime payedAt;
}
