package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by OKali on 2019/3/10.
 */
@SpringBootApplication
@EnableEurekaServer
public class Ch61App {

    public static void main(String[] args) {
        SpringApplication.run(Ch61App.class, args);
    }
}
