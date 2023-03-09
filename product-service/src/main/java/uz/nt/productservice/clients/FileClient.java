package uz.nt.productservice.clients;


import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


//@FeignClient(value = "file-service", url = "http://localhost:8001/file-service",configuration = FeignClientConfig.class)
@Configuration
@FeignClient(name = "file-service")
public interface FileClient {

    @PostMapping(value = "file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file);
}
