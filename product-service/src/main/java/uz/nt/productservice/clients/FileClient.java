package uz.nt.productservice.clients;

import dto.ResponseDto;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.config.FeignClientConfig;

@FeignClient(name = "file-service")
public interface FileClient {

    @PostMapping(value = "file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file);
}
