package uz.nt.fileservice.service;

import dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.dto.FileDto;

public interface Fileservices {

    ResponseDto<String> fileUpload(MultipartFile file);

}
