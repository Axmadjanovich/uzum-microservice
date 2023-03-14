package uz.nt.productservice.rest;

import dto.ErrorDto;
import dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.toList;
import static validator.AppStatusCodes.*;
import static validator.AppStatusMessages.*;

@RestControllerAdvice
public class ExceptionHandlerResource {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e) {
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
