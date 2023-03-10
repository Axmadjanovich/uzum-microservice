package uz.nt.emailservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.emailservice.service.EmailService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailResources {

    //TODO Har kuni 9:00 da scheduler ishlab, aksiya bo'layotgan mahsulotlar ro'yxatini emailga rasm va narxlari bilan
    // yuboradi.

    private final EmailService emailService;
    @PostMapping
    public ResponseDto<Boolean> sendMessage(@RequestParam String email, String message){
        return emailService.sendEmail(email,message);
    }
}
