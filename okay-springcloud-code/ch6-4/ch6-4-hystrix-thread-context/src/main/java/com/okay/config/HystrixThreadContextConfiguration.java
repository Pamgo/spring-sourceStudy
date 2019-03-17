package com.okay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by OKali on 2019/3/17.
 */
@Configuration
public class HystrixThreadContextConfiguration {

    @Bean
    public MyHystrixConcurrencyStrategy myHystrixConcurrencyStrategy() {
        return new MyHystrixConcurrencyStrategy();
    }
}
