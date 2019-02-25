package com.alison;

import com.alison.aop.cht.OrderService;
import com.alison.aop.cht.UserService;
import com.alison.bean.Person;
import com.alison.config.CarConfiguration;
import com.alison.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by OKali on 2018/8/12.
 */

public class MainTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aopbean.xml");
        UserService userService = context.getBean(UserService.class);
        OrderService orderService = context.getBean(OrderService.class);

        userService.createUser("Tom", "Cruise", 55);
        userService.queryUser();

        orderService.createOrder("Leo","随便买点什么！");
        orderService.queryOrder("Leo");

      /*  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarConfiguration.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }*/

    }

}
