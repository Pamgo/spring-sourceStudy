package com.alison.factorybean;


/**
 * Created by OKali on 2019/1/5.
 */
public class CarBuilder {

    private String make;

    private int year;

    public void setMake(String make) {
        this.make = make;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private static CarBuilder carBuilder = new CarBuilder();

    private CarBuilder(){}

    public static CarBuilder car() {
        return carBuilder;
    }

    // 模拟造车复杂过程
    public Car factory () {
        Car car = new Car();
        return car;
    }
}
