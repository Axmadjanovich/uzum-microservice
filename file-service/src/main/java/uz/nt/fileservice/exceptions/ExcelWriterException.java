package uz.nt.fileservice.exceptions;

import uz.nt.fileservice.service.impl.ExcelWriter;

public class ExcelWriterException extends RuntimeException{
    public ExcelWriterException(String message){
        super(message);
    }
}
