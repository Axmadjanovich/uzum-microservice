package uz.nt.emailservice.schedule;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.emailservice.service.EmailService;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {
    @Autowired
    EmailService emailService;
    @Transactional
    @Scheduled(cron = "0 9 0 * * *")
    public void sendEmailSaleProduct(){

    }
}
