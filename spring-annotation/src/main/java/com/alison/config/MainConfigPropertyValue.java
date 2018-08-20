package com.alison.config;

import com.alison.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by OKali on 2018/8/17.
 */
// 使用PropertySource读取外部配置文件中的k/v保存到运行的环境变量中
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class MainConfigPropertyValue {

    @Bean
    public Person person() {
        return new Person();
    }
}
