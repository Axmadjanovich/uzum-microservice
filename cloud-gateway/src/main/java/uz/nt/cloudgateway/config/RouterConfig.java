package uz.nt.cloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class RouterConfig {

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder){
//        return builder
//                .routes()
//                .route(r -> r.path("/product/**")
////                        .filters(f -> {
////                                    f.addRequestParameter("poddpo")
////                                }
////                                f.filter((exchange, chain) ->{
////                            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
////                            Map<String, String> newQueryParams = new HashMap<>(queryParams.toSingleValueMap());
////                            if (!queryParams.containsKey("size")){
////                                newQueryParams.put("size", "2");
////                            }
////                            if (!queryParams.containsKey("page")){
////                                newQueryParams.put("page", "1");
////                            }
////                            return chain.filter(exchange);
////                        }
////                        )
//                        .uri("lb://product-service"))
//                .route(r -> r.path("/user/*", "/user")
//                        .uri("lb://user-service"))
//                .build();
//    }
}
