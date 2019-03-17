package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * Created by OKali on 2019/3/10.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class Ch61ClientApp {

    public static void main(String[] args) {
        SpringApplication.run(Ch61ClientApp.class, args);
    }
}
