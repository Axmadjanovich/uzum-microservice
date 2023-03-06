package uz.nt.userservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Checker {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init(){
        System.out.println(environment.getProperty("test"));
    }
}
