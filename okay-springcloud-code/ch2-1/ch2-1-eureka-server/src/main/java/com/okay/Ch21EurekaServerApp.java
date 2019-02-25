package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by OKali on 2019/2/24.
 */
@SpringBootApplication
@EnableEurekaServer
public class Ch21EurekaServerApp {

    public static void main(String[] args) {
        SpringApplication.run(Ch21EurekaServerApp.class, args);
    }
}
