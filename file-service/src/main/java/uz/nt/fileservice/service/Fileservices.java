package uz.nt.fileservice.service;

import dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface Fileservices {

    ResponseDto<Integer> fileUpload(MultipartFile file);

}
