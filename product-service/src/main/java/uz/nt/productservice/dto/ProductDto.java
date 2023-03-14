package uz.nt.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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