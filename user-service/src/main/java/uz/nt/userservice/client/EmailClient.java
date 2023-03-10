package uz.nt.userservice.client;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.productservice.dto.ProductDto;

@FeignClient("email-service")
public interface EmailClient {

    @PostMapping("send")
    ResponseDto<Boolean> sentEmail(@RequestParam("email") String email,
                                                           @RequestParam("code") String code);
}
