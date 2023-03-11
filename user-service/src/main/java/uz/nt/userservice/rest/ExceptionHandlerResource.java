package uz.nt.userservice.rest;

import dto.ErrorDto;
import dto.ResponseDto;
import org.apache.http.MethodNotSupportedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerResource {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException m){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                        .message(AppStatusMessages.VALIDATION_ERROR)
                        .errors(m.getBindingResult().getFieldErrors()
                                .stream()
                                .map(f -> new ErrorDto(f.getField(), f.getDefaultMessage()))
                                .collect(Collectors.toList()))
                        .build());
    }
}
