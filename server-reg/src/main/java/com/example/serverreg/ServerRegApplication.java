package com.example.serverreg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerRegApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerRegApplication.class, args);
    }

}
