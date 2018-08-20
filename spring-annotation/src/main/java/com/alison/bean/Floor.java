package com.alison.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by OKali on 2018/8/17.
 */
@Component
public class Floor implements InitializingBean,DisposableBean {

    public Floor() {
        System.out.println("Floor constructor ...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化方法
        System.out.println("floor ... afterPropertiesSet...");
    }

    @Override
    public void destroy() throws Exception {
        // 销毁方法
        System.out.println("floor ... destroy...");
    }
}
