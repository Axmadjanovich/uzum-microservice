package uz.nt.productservice.clients;


import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.config.ForeignClientConfig;

@FeignClient(name = "file-service", url = "http://localhost:8001/file-service",configuration = ForeignClientConfig.class)
public interface Clients {

    @PostMapping(value = "/file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile multipartFile);
}
