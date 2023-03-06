package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Integer id;
    private String name;
    private String extension;
}
