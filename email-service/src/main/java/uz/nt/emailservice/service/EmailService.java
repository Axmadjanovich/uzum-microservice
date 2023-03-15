package uz.nt.emailservice.service;

import dto.ResponseDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.nt.emailservice.clients.FileClient;
import uz.nt.emailservice.clients.ProductClient;
import uz.nt.emailservice.clients.SalesClient;
import uz.nt.emailservice.dto.ProductDto;
import uz.nt.emailservice.dto.SalesDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final FileClient fileClient;
    private final ProductClient productClient;
    private final SalesClient salesClient;
    private final String htmlCode;

    public ResponseDto<Boolean> sendEmail(String toEmail, String code){
        try {
            MimeMessage sendMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(sendMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Uzum verify");

            String url = "http://localhost:9006/user/verify?email=" + toEmail + "&code=" + code;

            String htmlMessage = htmlCode.replaceFirst("linebreak",url);

            helper.setText(htmlMessage, true);

            mailSender.send(sendMessage);

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

    public ResponseDto<Boolean> sendEmailAboutSalesProduct(String email){
        List<SalesDto> sales = salesClient.getSalesProductExp().getData();
        try {
            MimeMessage sendMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(sendMessage, true);
            helper.setTo(email);
            helper.setSubject("Uzum marketda jegirma tugashiga bir kun qoldi");
            String htmlBody = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Uzum</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Name</th>\n" +
                    "    <th>OldPrice</th>\n" +
                    "    <th>New Price</th>\n" +
                    "    <th>Image</th>\n"+
                    "  </tr>";

            List<ProductDto> products = productClient.getProductsById(sales.stream().map(i -> i.getId()).toList()).getData().toList();


            for (ProductDto product : products) {
                htmlBody+="<tr>\n" +
                        "    <td>"+product.getName()+"</td>\n" +
                        "    <td>"+sales.stream().filter(p -> product.getId().equals(p.getProductId())).findFirst().get().getPrice()+"</td>\n" +
                        "    <td>"+product.getPrice()+"</td>\n" +
                        "    <td><img src='data:image/png;base64," + fileClient.getFileBytes(product.getFileId(),"SMALL")+"></img></td>\n"+
                        "  </tr>";
            }
            htmlBody+="</table>\n" +
                    "</body>\n" +
                    "</html>";
            helper.setText(htmlBody, true);
            helper.setSubject("Jegirmaning ohirgi kuni");


            mailSender.send(sendMessage);

            return ResponseDto.<Boolean>builder()
                    .message("OK")
                    .code(0)
                    .success(true)
                    .data(true)
                    .build();

        }catch (Exception e) {
            return ResponseDto.<Boolean>builder()
                    .message("Error: " + e.getMessage())
                    .code(-2)
                    .data(false)
                    .success(false)
                    .build();
        }
    }
}