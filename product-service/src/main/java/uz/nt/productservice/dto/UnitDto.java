package uz.nt.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static validator.AppStatusMessages.EMPTY_STRING;
import static validator.AppStatusMessages.SIZE_MISMATCH;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String shortName;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    private String description;
}
