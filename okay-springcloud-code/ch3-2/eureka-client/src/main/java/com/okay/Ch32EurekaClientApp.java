package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by OKali on 2019/2/25.
 */
@SpringBootApplication
@EnableEurekaClient
public class Ch32EurekaClientApp {

    public static void main(String[] args) {
        SpringApplication.run(Ch32EurekaClientApp.class, args);
    }
}
