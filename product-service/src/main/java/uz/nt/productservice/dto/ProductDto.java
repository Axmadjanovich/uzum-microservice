package uz.nt.productservice.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private CategoryDto category;
    private MultipartFile image;
    private Boolean isAvailable;
    private UnitDto units;
    private Integer indicator;

}