package uz.nt.userservice.client;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.productservice.dto.ProductDto;

@FeignClient("product-service")
public interface ProductClient {

    @GetMapping("product")
    ResponseDto<Page<EntityModel<ProductDto>>> getProducts(@RequestParam(defaultValue = "10") Integer size,
                                                           @RequestParam(defaultValue = "0") Integer page);
}
