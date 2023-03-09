package uz.nt.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {
    private Integer id;
    private String shortName;
    private String name;
    private String description;
}
