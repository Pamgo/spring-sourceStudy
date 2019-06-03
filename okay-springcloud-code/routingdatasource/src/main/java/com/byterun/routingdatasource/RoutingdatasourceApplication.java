package com.byterun.routingdatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RoutingdatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutingdatasourceApplication.class, args);
    }

}
