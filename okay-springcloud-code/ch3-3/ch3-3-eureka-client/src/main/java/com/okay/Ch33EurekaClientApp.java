package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by OKali on 2019/2/26.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Ch33EurekaClientApp {

    public static void main(String[] args) {
        SpringApplication.run(Ch33EurekaClientApp.class, args);
    }
}
