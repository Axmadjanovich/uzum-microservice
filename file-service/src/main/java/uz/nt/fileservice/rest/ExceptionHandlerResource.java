package uz.nt.fileservice.rest;

import dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.fileservice.exceptions.FileConvertingException;

import static validator.AppStatusCodes.UNEXPECTED_ERROR_CODE;
import static validator.AppStatusMessages.UNEXPECTED_ERROR;

@RestControllerAdvice
public class ExceptionHandlerResource {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> fileConvertingException(FileConvertingException e) {
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .message(UNEXPECTED_ERROR + ": " + e.getMessage())
                        .code(UNEXPECTED_ERROR_CODE)
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> excelWriterException(FileConvertingException e) {
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .message(UNEXPECTED_ERROR + ": Error while writing report to excel file. " + e.getMessage())
                        .code(UNEXPECTED_ERROR_CODE)
                        .build());
    }

}
