package com.okay.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by OKali on 2019/1/29.
 * xxxAware的意思是，你在Spring里面想要什么，例如xxx
 * PriorityOrdered优先级排序，初始化bean的顺序
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object>  dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() { // bean初始化顺序,值越小优先级越高
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    // 从缓存中获取
    public static <T> T of(Class<T> clazz) {
        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {
            return instance;
        }
        dataTableMap.put(clazz, bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    public static <T> T bean (String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T bean (Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

}
