package uz.nt.emailservice.clients;

import dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uz.nt.emailservice.dto.SalesDto;

import java.util.List;

//@FeignClient
public interface SalesClient {
    @GetMapping("/sales")
    ResponseDto<List<SalesDto>> getSalesProductExp();
}
