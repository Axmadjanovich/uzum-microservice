package uz.nt.emailservice.schedule;

import dto.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.emailservice.clients.UserClient;
import uz.nt.emailservice.dto.UsersDto;
import uz.nt.emailservice.exception.UserNotFoundException;
import uz.nt.emailservice.service.EmailService;

import java.util.List;

@EnableScheduling
@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ScheduleJob {

    private final UserClient userClient;
    private final EmailService emailService;


    @Transactional
    @Scheduled(cron = "0 0 9 * * *")
    public void sendEmailSales() throws UserNotFoundException {
        ResponseDto<List<UsersDto>> responseDto = userClient.getUsers();

        if(!responseDto.isSuccess()){
            throw new UserNotFoundException();
        }
        List<UsersDto> users = responseDto.getData();
        users.forEach(u -> emailService.sendEmailAboutSalesProduct(u.getEmail()));

    }
}
