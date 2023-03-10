package uz.nt.emailservice.dto;

import lombok.Getter;

@Getter
public class ProductDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private Integer categoryId;
}
