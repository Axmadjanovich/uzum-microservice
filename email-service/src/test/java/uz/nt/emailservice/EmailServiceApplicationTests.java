package uz.nt.emailservice;

import dto.ResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.emailservice.service.EmailService;

@SpringBootTest
class EmailServiceApplicationTests {
    @Autowired
    private EmailService emailService;
    @Test
    void sendSalesProduct() {
        ResponseDto<Boolean> booleanResponseDto = emailService.sendEmailAboutSalesProduct("yusupofavazbek@gmail.com");
        Assertions.assertEquals(booleanResponseDto.getData(),true);
    }

}
