package uz.nt.emailservice.service;

import dto.ResponseDto;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

@Service
@RequiredArgsConstructor
public class EmailService {
    //private final JavaMailSender mailSender;
//    @Value("${spring.mail.username}")
//    private final String username;
//
//    @Value("${spring.mail.password}")
//    private final String password;
    @Autowired
    private Session session;

//    public ResponseDto<Boolean> sendEmail(String toEmail, String message) {
//        try {
//            SimpleMailMessage email = new SimpleMailMessage();
//            email.setFrom("JavaN4");
//            email.setTo(toEmail);
//            email.setSubject("Java N4");
//            email.setText(message);
//
//            mailSender.send(email);
//
//            return ResponseDto.<Boolean>builder()
//                    .message("OK")
//                    .code(0)
//                    .success(true)
//                    .data(true)
//                    .build();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseDto.<Boolean>builder()
//                    .message("Error: " + e.getMessage())
//                    .code(-2)
//                    .data(false)
//                    .success(false)
//                    .build();
//        }
//    }

    public ResponseDto<Boolean> sendVerify(String email, String messagee) {
        MimeMessage message = new MimeMessage(session);
        try {

            message.setFrom(new InternetAddress("avazbekyusupov857@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email)
            );
            message.setSubject("Uzum verify");

            String messageText = "<h1>Push this button to verify</h1>" +
                    "<a href=><button>VERIFY</button>";
            MimeBodyPart part = new MimeBodyPart();
            part.setContent(messageText,"text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(part);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            return ResponseDto.<Boolean>builder()
                    .message(e.)
                    .code(AppStatusCodes.UNEXPECTED_ERROR_CODE)
                    .build();
        }
        return ResponseDto.<Boolean>builder()
                .data(true)
                .success(true)
                .message(AppStatusMessages.OK)
                .build();
    }
}
