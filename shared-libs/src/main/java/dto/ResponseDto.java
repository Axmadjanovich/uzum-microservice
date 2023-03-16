package dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> extends RepresentationModel<ResponseDto<T>> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorDto> errors;
}