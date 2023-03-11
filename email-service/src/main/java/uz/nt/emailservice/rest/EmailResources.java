package uz.nt.emailservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.emailservice.clients.SalesClient;
import uz.nt.emailservice.clients.UserClient;
import uz.nt.emailservice.dto.SalesDto;
import uz.nt.emailservice.dto.UsersDto;
import uz.nt.emailservice.service.EmailService;

import java.util.List;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailResources {

    //TODO Har kuni 9:00 da scheduler ishlab, aksiya bo'layotgan mahsulotlar ro'yxatini emailga rasm va narxlari bilan
    // yuboradi.

    private final EmailService emailService;
    private final SalesClient salesClient;
    private final UserClient userClient;
    @PostMapping
    public ResponseDto<Boolean> sendVerifyCode(@RequestParam String email, @RequestParam String message){
        return emailService.sendEmail(email,message);
    }
    @GetMapping
    ResponseDto<List<SalesDto>> getSales(){
        return salesClient.getSalesProductExp();
    }
    @GetMapping("/{id}")
    ResponseDto<UsersDto> getUser(@PathVariable Integer id){
        return userClient.getEmail(60);
    }
    @GetMapping("pr")
    ResponseDto<Boolean> getEmail(@RequestParam String email){
        return emailService.sendEmailAboutSalesProduct(email);
    }
}
