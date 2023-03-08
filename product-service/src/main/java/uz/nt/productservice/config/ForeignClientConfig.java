package uz.nt.productservice.config;

import feign.Logger;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ForeignClientConfig {

    @Autowired
    ObjectFactory<HttpMessageConverters> objectFactory;

    @Bean
    public SpringFormEncoder encoder(){
        return new SpringFormEncoder(new SpringEncoder(objectFactory));
    }

    @Bean
    public Logger.Level feignLogger(){
        return Logger.Level.FULL;
    }

}
