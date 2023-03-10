package uz.nt.emailservice.dto;

import lombok.Getter;

import java.util.Date;
@Getter
public class SalesDto {
    private Integer id;
    private Integer product_id;
    private Double price;
    private Date expressionDate;
}
