package uz.nt.emailservice.clients;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.emailservice.dto.ProductDto;

import java.util.List;
import java.util.stream.Stream;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("product/for-sales")
    ResponseDto<Stream<ProductDto>> getProductsById(@RequestParam List<Integer> productIds);

}
