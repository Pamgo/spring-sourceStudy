package com.alison;

import com.alison.bean.Person;
import com.alison.config.MainConfig;
import com.alison.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * Created by OKali on 2018/8/12.
 */
public class IOCTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @Test
    public void test01 () {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
      String[] names = applicationContext.getBeanDefinitionNames();
      for (String name : names) {
          System.out.println(name);
      }
    }

    @Test
    public void test02 () {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("容器创建完毕。。。");
      /*  String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }*/
        Object person = applicationContext.getBean("person");
        Object person2 = applicationContext.getBean("person");
        System.out.println(person == person2);
    }

    @Test
    public void test03() {
        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 获取环境变量
        String property = environment.getProperty("os.name");

        System.out.println(property);
        for (String name : namesForType) {
            System.out.println(name);
        }
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.out.println(beansOfType);
    }

    @Test
    public void test04() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name :beanDefinitionNames) {
            System.out.println(name);
        }
        // 工厂Bean获取的是调用getObject创建对象
        Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
        System.out.println(colorFactoryBean.getClass());

        // 通过加&前缀，获取factoryBean
        Object bean = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean);
    }
}
