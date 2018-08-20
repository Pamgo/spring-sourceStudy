package com.alison.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * bean的后置处理器，在bean初始化前后做处理
 * Created by OKali on 2018/8/17.
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("...postProcessBeforeInitialization..."+beanName+"==>" +bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("...postProcessAfterInitialization..."+beanName+"==>" +bean);
        return bean;
    }
}
