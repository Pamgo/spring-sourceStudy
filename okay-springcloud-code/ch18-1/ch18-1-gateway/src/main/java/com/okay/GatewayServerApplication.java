package com.okay;

import com.okay.filter.CustomGatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GatewayServerApplication {

   // @Bean
    public RouteLocator customoerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/test")
                .filters(f -> f.filter(new CustomGatewayFilter()))
                .uri("http://localhost:8000/hello/okay")
                .order(0)
                .id("custom_filter")).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
