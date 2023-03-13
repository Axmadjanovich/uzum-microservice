package uz.nt.fileservice.client;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("product/report")
    ResponseDto<List<ProductDto>> getProductsForReport();
}
