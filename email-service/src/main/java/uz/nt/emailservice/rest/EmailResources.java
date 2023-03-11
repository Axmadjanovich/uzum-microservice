package uz.nt.emailservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.emailservice.clients.FileClient;
import uz.nt.emailservice.clients.ProductClient;
import uz.nt.emailservice.clients.SalesClient;
import uz.nt.emailservice.clients.UserClient;
import uz.nt.emailservice.dto.ProductDto;
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
    private final ProductClient productClient;
    private final FileClient fileClient;
    @PostMapping("/sendEmail")
    public ResponseDto<Boolean> sendVerifyCode(@RequestParam String email, @RequestParam String message){
        return emailService.sendEmail(email,message);
    }
    @GetMapping
    ResponseDto<List<SalesDto>> testSalesClient(){
        return salesClient.getSalesProductExp();
    }
    @GetMapping("/user")
    ResponseDto<List<UsersDto>> testUserClient(){
        return userClient.getUsers();
    }
    @GetMapping("pr")
    ResponseDto<ProductDto> testProductClient(@RequestParam Integer id){
        return productClient.getProductById(id);
    }
    @GetMapping("file")
    ResponseDto<byte[]> testFileClient(@RequestParam Integer fileId){
        return fileClient.getFileBytes(fileId, "small");
    }
    @GetMapping("email")
    ResponseDto<Boolean> testEmail(@RequestParam String email){
        return emailService.sendEmailAboutSalesProduct(email);
    }
}
