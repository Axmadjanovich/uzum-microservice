package uz.nt.fileservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation for File service",
                description = "It is a service than you can save and find your images",
                contact = @Contact(name = "ITeam",email = "iTeam@gmail.com",url = "t.me/...")
        )
)
public class AppConfig {
}
