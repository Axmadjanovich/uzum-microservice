package uz.nt.emailservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.emailservice.service.EmailService;

@SpringBootTest
class EmailServiceApplicationTests {
    @Autowired
    EmailService emailService;
    @Test
    void sendSalesProduct() {
        emailService.sendEmailAboutSalesProduct("yusupofavazbek@gmail.com");
    }

}
