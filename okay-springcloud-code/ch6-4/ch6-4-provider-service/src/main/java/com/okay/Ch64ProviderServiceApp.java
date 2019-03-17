package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by OKali on 2019/3/17.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Ch64ProviderServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(Ch64ProviderServiceApp.class, args);
    }
}
