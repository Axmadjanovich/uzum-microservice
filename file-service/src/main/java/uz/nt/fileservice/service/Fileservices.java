package uz.nt.fileservice.service;

import dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Fileservices {

    ResponseDto<Integer> fileUpload(MultipartFile file);
    ResponseDto<byte[]> getFileById(Integer id) throws IOException;

}
