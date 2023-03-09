package uz.nt.productservice.clients;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.config.FeignClientConfig;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

import java.util.List;

//@FeignClient(name = "file-service",
//        url = "http://localhost:8001/file-service", configuration = FeignClientConfig.class)
@FeignClient(name = "file-service")
public interface FileClient {

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/file/report")
    ResponseDto<Integer> sendProductsWithLessAmount(@RequestBody List<ProductDto> productList);
}
