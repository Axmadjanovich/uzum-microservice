package uz.nt.fileservice.service;

import dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.dto.ProductDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Fileservices {

    ResponseDto<Integer> fileUpload(MultipartFile file);

    void reportProducts(List<ProductDto> productDtoList) throws IOException;

    ResponseDto<Integer> fileUpload2(MultipartFile file);

    ResponseDto<byte[]> getFileById(Integer fileId) throws IOException;
}
