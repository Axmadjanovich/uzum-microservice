package uz.nt.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static validator.AppStatusMessages.EMPTY_STRING;
import static validator.AppStatusMessages.SIZE_MISMATCH;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    @Size(max = 10, min = 1,message = SIZE_MISMATCH)
    private String shortName;
    @NotBlank(message = EMPTY_STRING)
    @Size(max = 100, min = 3,message = SIZE_MISMATCH)
    private String name;
    private String description;
}
