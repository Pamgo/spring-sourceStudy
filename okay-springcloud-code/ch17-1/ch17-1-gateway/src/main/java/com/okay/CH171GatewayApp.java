package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Created by OKali on 2019/2/27.
 */
@SpringBootApplication
public class CH171GatewayApp {

    /**
     * 基本的转发
     * 当访问http://localhost:8080/jd
     * 转发到http://jd.com
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r.path("/jd")
        .uri("http://jd.com:80/").id("jd_route")).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CH171GatewayApp.class, args);
    }
}
