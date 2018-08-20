package com.alison.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by OKali on 2018/8/14.
 * 是否linux系统
 */
public class LinuxCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 是否windows系统
        // 1、能获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 2、获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        // 3、获取当前环境信息
        Environment environment = context.getEnvironment();
        // 4、获取到bean定义的注册类（可以注册，移除等）
        BeanDefinitionRegistry registry = context.getRegistry();

        String property = environment.getProperty("os.name");

        // 判断容器中是否包含person bean定义
        boolean definition = registry.containsBeanDefinition("person");

        if (property.contains("linux")) {
            return true;
        }
        return false;
    }

}
