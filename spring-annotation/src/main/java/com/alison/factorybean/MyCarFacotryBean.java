package com.alison.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

/**
 * Created by OKali on 2019/1/5.
 * 我们假设现在需要创建一个 Person 的 Bean，首先我们需要一个 Car 的实例，
 * 我们这里假设 Car 的实例创建很麻烦，那么我们可以把创建 Car 的复杂过程包装起来
 */
public class MyCarFacotryBean implements FactoryBean<Car> {

    private String make;

    private int year;

    public void setMake(String make) {
        this.make = make;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public Car getObject() throws Exception {
        CarBuilder cb = CarBuilder.car();

        if (year == 0) cb.setYear(this.year);
        if (StringUtils.hasText(this.make)) cb.setMake(this.make);
        // TODO

        return cb.factory();
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
