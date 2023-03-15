package uz.nt.cloudgateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HystrixController {

    @RequestMapping("/userFallback")
    public Mono<String> userFallback(){
        return Mono.just("User service responce takes long time or shot down");
    }


    @RequestMapping("/productFallback")
    public Mono<String> productFallback(){
        return Mono.just("Product service responce takes long time or shot down");
    }
}
