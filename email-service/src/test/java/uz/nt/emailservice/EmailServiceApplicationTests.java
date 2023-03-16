package uz.nt.emailservice;

import dto.ResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.nt.emailservice.service.EmailService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceApplicationTests {
    @Autowired
    EmailService emailService;
    @Test
    @DisplayName("send sales product to yufupovavazbek@gmail.com")
    void sendSalesProduct() {
        ResponseDto<Boolean> resp = emailService.sendEmailAboutSalesProduct("yusupofavazbek@gmail.com");
        assertEquals(resp.getCode(),0,"Response code is not equal 0");
        assertEquals(resp.getMessage(),"OK","Response message is not OK");
        assertNull(resp.getErrors(), "Response error is not empty");
        assertTrue(resp.isSuccess(),"Response failed");
    }

    @Test
    @DisplayName("send email to yufupovavazbek@gmail.com")
    void sendEmail() {
        ResponseDto<Boolean> resp = emailService.sendEmail("yusupofavazbek@gmail.com","only for test");
        assertEquals(resp.getCode(),0,"Response code is not equal 0");
        assertEquals(resp.getMessage(),"OK","Response message is not OK");
        assertNull(resp.getErrors(), "Response error is not empty");
        assertTrue(resp.isSuccess(),"Response failed");


    }

}
