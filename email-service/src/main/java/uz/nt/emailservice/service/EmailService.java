package uz.nt.emailservice.service;

import dto.ResponseDto;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.nt.emailservice.clients.FileClient;
import uz.nt.emailservice.clients.ProductClient;
import uz.nt.emailservice.clients.SalesClient;
import uz.nt.emailservice.dto.ProductDto;
import uz.nt.emailservice.dto.SalesDto;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final FileClient fileClient;
    private final ProductClient productClient;
    private final SalesClient salesClient;

    public ResponseDto<Boolean> sendEmail(String toEmail, String code){
        try {
            MimeMessage sendMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(sendMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Uzum verify");

            String url = "http://localhost:9006/user/verify?email=" + toEmail + "&code=" + code;
            String htmlMessage = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "  <title>Email Confirmation</title>\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <style type=\"text/css\">\n" +
                    "  @media screen {\n" +
                    "    @font-face {\n" +
                    "      font-family: 'Source Sans Pro';\n" +
                    "      font-style: normal;\n" +
                    "      font-weight: 400;\n" +
                    "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                    "    }\n" +
                    "    @font-face {\n" +
                    "      font-family: 'Source Sans Pro';\n" +
                    "      font-style: normal;\n" +
                    "      font-weight: 700;\n" +
                    "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                    "    }\n" +
                    "  }\n" +
                    "  body,\n" +
                    "  table,\n" +
                    "  td,\n" +
                    "  a {\n" +
                    "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                    "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                    "  }\n" +
                    "  \n" +
                    "  table,\n" +
                    "  td {\n" +
                    "    mso-table-rspace: 0pt;\n" +
                    "    mso-table-lspace: 0pt;\n" +
                    "  }\n" +
                    "    img {\n" +
                    "    -ms-interpolation-mode: bicubic;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Remove blue links for iOS devices.\n" +
                    "   */\n" +
                    "  a[x-apple-data-detectors] {\n" +
                    "    font-family: inherit !important;\n" +
                    "    font-size: inherit !important;\n" +
                    "    font-weight: inherit !important;\n" +
                    "    line-height: inherit !important;\n" +
                    "    color: inherit !important;\n" +
                    "    text-decoration: none !important;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Fix centering issues in Android 4.4.\n" +
                    "   */\n" +
                    "  div[style*=\"margin: 16px 0;\"] {\n" +
                    "    margin: 0 !important;\n" +
                    "  }\n" +
                    "  body {\n" +
                    "    width: 100% !important;\n" +
                    "    height: 100% !important;\n" +
                    "    padding: 0 !important;\n" +
                    "    margin: 0 !important;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Collapse table borders to avoid space between cells.\n" +
                    "   */\n" +
                    "  table {\n" +
                    "    border-collapse: collapse !important;\n" +
                    "  }\n" +
                    "  a {\n" +
                    "    color: #1a82e2;\n" +
                    "  }\n" +
                    "  img {\n" +
                    "    height: auto;\n" +
                    "    line-height: 100%;\n" +
                    "    text-decoration: none;\n" +
                    "    border: 0;\n" +
                    "    outline: none;\n" +
                    "  }\n" +
                    "  </style>\n" +
                    "\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #e9ecef;\">\n" +
                    "\n" +
                    "  <!-- start preheader -->\n" +
                    "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
                    "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                    "  </div>\n" +
                    "  <!-- end preheader -->\n" +
                    "\n" +
                    "  <!-- start body -->\n" +
                    "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "\n" +
                    "    <!-- start logo -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                    "              <a href=\"https://www.blogdesire.com\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                    "              </a>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                    "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Confirm Your Email Address</h1>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "\n" +
                    "    <!-- start copy block -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "              <p style=\"margin: 0;\">Tap the button below to confirm your email address.</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "          <!-- start button -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                    "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                <tr>\n" +
                    "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                    "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                      <tr>\n" +
                    "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                    "                          <a href='"+url+"' target=\"_blank\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Verify Email</a>\n" +
                    "                        </td>\n" +
                    "                      </tr>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end button -->\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                    "              <p style=\"margin: 0;\">Cheers,<br> Paste</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end copy block -->\n" +
                    "\n" +
                    "    <!-- start footer -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start permission -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                    "              <p style=\"margin: 0;\">You received this email because we received a request for [type_of_action] for your account. If you didn't request [type_of_action] you can safely delete this email.</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end permission -->\n" +
                    "\n" +
                    "          <!-- start unsubscribe -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                    "              <p style=\"margin: 0;\">To stop receiving these emails, you can <a href=\"https://www.blogdesire.com\" target=\"_blank\">unsubscribe</a> at any time.</p>\n" +
                    "              <p style=\"margin: 0;\">Paste 1234 S. Broadway St. City, State 12345</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end unsubscribe -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end footer -->\n" +
                    "\n" +
                    "  </table>\n" +
                    "  <!-- end body -->\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

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
        List<SalesDto> products = salesClient.getSalesProductExp().getData();
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

            for (SalesDto product : products) {
                ProductDto productDto = productClient.getProductById(product.getProduct_id()).getData();
                htmlBody+="<tr>\n" +
                        "    <td>"+productDto.getName()+"</td>\n" +
                        "    <td>"+productDto.getPrice()+"</td>\n" +
                        "    <td>"+product.getPrice()+"</td>\n" +
                        "    <td></td\n"+
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
