package com.okay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by OKali on 2019/2/24.
 */
@SpringBootApplication
@EnableConfigServer
public class Ch31ConfigServerApp {

    public static void main(String[] args) {
        SpringApplication.run(Ch31ConfigServerApp.class, args);
    }
}
