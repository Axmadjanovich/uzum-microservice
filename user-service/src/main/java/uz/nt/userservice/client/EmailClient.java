package uz.nt.userservice.client;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("email-service")
public interface EmailClient {
    @PostMapping("email")
    ResponseDto<Boolean> sendEmail(@RequestParam String email, @RequestParam String message);
}
