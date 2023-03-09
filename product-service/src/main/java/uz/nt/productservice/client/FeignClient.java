package uz.nt.productservice.client;

import dto.ResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.config.FeignClientConfig;

@org.springframework.cloud.openfeign.FeignClient(value = "file",
        url = "http://localhost:8080/file",
        configuration = FeignClientConfig.class)
public interface FeignClient {

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file);
}
