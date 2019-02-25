package com.okay.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by OKali on 2019/1/19.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     *
     * @param converters 消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear(); // 清空转换器
        // 只使用一个转换器，
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
