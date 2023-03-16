package uz.nt.userservice.rest;

import dto.ErrorDto;
import dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.userservice.exceptions.ConnectionException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConnectionException.class)
    public ResponseEntity<ResponseDto<ErrorDto>> connectionError(ConnectionException e){
        return ResponseEntity.badRequest().body(
                ResponseDto.<ErrorDto>builder()
                        .data(new ErrorDto(e.getField(),e.getMessage()))
                        .message(AppStatusMessages.UNEXPECTED_ERROR)
                        .code(AppStatusCodes.UNEXPECTED_ERROR_CODE)
                        .build()
        );
    }


}
