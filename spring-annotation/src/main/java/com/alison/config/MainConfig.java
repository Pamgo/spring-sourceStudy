package com.alison.config;

import com.alison.bean.Person;
import com.alison.service.BookService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Created by OKali on 2018/8/12.
 * @ComponentScan value:指定要扫描的包
 * excludeFilters = Filter[] ，指定扫描的时候按照什么规则排除
 * includeFilters = Filter[] , 指定扫描的时候只需要包含哪些组件
 *   （需要配置一个开启才生效，userDefaultFilters = false）
 * FilterType.ANNOTATION ， 按照注解
 * FilterType.ASSIGNABLE_TYPE, 指定给定类型
 * FilterType.ASPECTJ, 使用aspectj表达式（不常用）
 * FilterType.Regex, 使用正则表达式（不常用）
 * FilterType.CUSTOM, 自定义类型 MyTypeFilter
 */
// 配置类==配置文件
@Configuration
@ComponentScan(value = "com.alison",includeFilters = {
        // 排除注解
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
}, useDefaultFilters = false)
public class MainConfig {

    @Bean
    public Person person() {
        return new Person("1","alison");
    }

}
