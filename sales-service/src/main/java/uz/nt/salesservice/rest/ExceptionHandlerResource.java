package uz.nt.salesservice.rest;

import dto.ErrorDto;
import dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static validator.AppStatusCodes.VALIDATION_ERROR_CODE;
import static validator.AppStatusMessages.VALIDATION_ERROR;

@RestControllerAdvice
public class ExceptionHandlerResource {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(RuntimeException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message(VALIDATION_ERROR)
                        .errors(List.of(new ErrorDto(e.toString(),e.getMessage())))
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message(VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors()
                                .stream()
                                .map(f -> new ErrorDto(f.getField(), f.getDefaultMessage()))
                                .collect(toList()))
                        .build());
    }
}
