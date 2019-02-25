package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by OKali on 2019/2/25.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class Ch32EurekaGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(Ch32EurekaGatewayApp.class, args);
    }
}
