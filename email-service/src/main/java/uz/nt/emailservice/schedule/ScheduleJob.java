package uz.nt.emailservice.schedule;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.emailservice.clients.UserClient;
import uz.nt.emailservice.dto.UsersDto;
import uz.nt.emailservice.service.EmailService;

import java.util.List;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {
//    @Autowired
//    UserClient userClient;
    @Autowired
    EmailService emailService;
    @Transactional
    @Scheduled(cron = "0 2 * * * *")
    public void sendEmailSaleProduct(){
        //UsersDto users = userClient.getEmail(60).getData();
        //emailService.sendEmailAboutSalesProduct(users.getEmail());
    }
}
