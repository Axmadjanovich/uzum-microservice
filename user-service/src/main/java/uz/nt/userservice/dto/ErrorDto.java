package uz.nt.userservice.dto;

import dto.ResponseDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto<T> extends RepresentationModel<ResponseDto<T>> {
    private String field;
    private String error;
    private boolean success;
    private T data;
    private List<ErrorDto> errors;
}
