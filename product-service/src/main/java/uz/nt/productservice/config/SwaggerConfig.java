package uz.nt.productservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration

@OpenAPIDefinition(
        info = @Info(
                title = "Documentation for Uzum Market",
                description = "Product Service",
                contact = @Contact(name = "Mahmud Saidxonov", email = "saidxonovmaxmudjon@gmail.com", url = "https://t.me/mahmud_s"),
                version = "1.0.0"
        )

)
public class SwaggerConfig {
}
