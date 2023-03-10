package uz.nt.emailservice.clients;
import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.emailservice.dto.ProductDto;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping
    ResponseDto<ProductDto> getProductBtId(@RequestParam Integer id);
}
