package com.alison;

import com.alison.config.MainConfigProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by OKali on 2018/8/18.
 */
public class IOCTestProfile {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MainConfigProfile.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void test02() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MainConfigProfile.class);
        String[] beanNamesForType = context.getBeanNamesForType(DataSource.class);
        for (String name :
                beanNamesForType) {
            System.out.println(name);

        }
    }

    // 代码的方式激活某种环境
    @Test
    public void test03(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 1、创建一个无参构造器
        // 2、设置需要激活的环境
        context.getEnvironment().setActiveProfiles("test");
        // 3、注册主配置类
        context.register(MainConfigProfile.class);
        // 4、启动刷新容器
        context.refresh();

        String[] beanNamesForType = context.getBeanNamesForType(DataSource.class);
        for (String name :
                beanNamesForType) {
            System.out.println(name);
        }
        Object yellow = context.getBean("yellow");
        System.out.println(yellow);
    }
}
