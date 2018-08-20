package com.alison.condition;

import com.alison.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by OKali on 2018/8/14.
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @param importingClassMetadata  当前类的注解信息
     * @param registry ： BeanDefinition注册类
     *                 把所有需要添加到容器中的bean,
     *                 调用BeanDefinitionRegistry.registerBeanDefinition
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("com.alison.bean.Red");
        boolean blue = registry.containsBeanDefinition("com.alison.bean.Blue");
        if (red && blue) {
            // 指定bean定义的类型。。。
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            // 注册指定bean名
            registry.registerBeanDefinition("rainBow", rootBeanDefinition);
        }
    }
}
