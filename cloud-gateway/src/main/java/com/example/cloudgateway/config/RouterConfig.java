package com.example.cloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("user-service",p->p.path("/user","/user/*")
                        .filters(f->f.addRequestParameter("page","0"))
                        .uri("lb://user-service"))
                .route(p->p.path("/product","/product/*")
                        .uri("lb://product-service"))
                .route(p->p
                        .path("/sales")
                        .and()
                        .header("java")
                        .uri("lb://sales-service"))
                .build();

    }
}
