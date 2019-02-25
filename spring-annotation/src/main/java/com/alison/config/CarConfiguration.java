package com.alison.config;

import com.alison.factorybean.MyCarFacotryBean;
import com.alison.factorybean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by OKali on 2019/1/5.
 */
@Configuration
public class CarConfiguration {

    @Bean
    public MyCarFacotryBean carFacotryBean() {
        MyCarFacotryBean cfb = new MyCarFacotryBean();
        cfb.setMake("alison");
        cfb.setYear(1992);
        return cfb;
    }

    @Bean
    public Person aPerson () throws Exception {
        Person person = new Person();
        person.setCar(carFacotryBean().getObject());
        return person;
    }
}
