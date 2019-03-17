package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by OKali on 2019/3/17.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class Ch64HystrixCacheApp {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Ch64HystrixCacheApp.class, args);
    }
}
