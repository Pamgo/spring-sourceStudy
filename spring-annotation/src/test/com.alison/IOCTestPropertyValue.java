package com.alison;

import com.alison.bean.Person;
import com.alison.config.MainConfigPropertyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by OKali on 2018/8/17.
 */
public class IOCTestPropertyValue {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigPropertyValue.class);

    @Test
    public void test01() {
        printBean(context);
        System.out.println("================");
        Person person = (Person) context.getBean("person");
        System.out.println(person);

        ConfigurableEnvironment environment = context.getEnvironment();
        String property = environment.getProperty("person.pickName");
        System.out.println(property);

        context.close();
    }

    private void printBean(AnnotationConfigApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
