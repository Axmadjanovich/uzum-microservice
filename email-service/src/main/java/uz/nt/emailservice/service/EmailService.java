package uz.nt.emailservice.service;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    public ResponseDto<Boolean> sendEmail(String toEmail, String message){
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("JavaN4");
            email.setTo(toEmail);
            email.setSubject("Java N4");
            email.setText(message);

            mailSender.send(email);

            return ResponseDto.<Boolean>builder()
                    .message("OK")
                    .code(0)
                    .success(true)
                    .data(true)
                    .build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseDto.<Boolean>builder()
                    .message("Error: "+e.getMessage())
                    .code(-2)
                    .data(false)
                    .success(false)
                    .build();
        }
    }
}
