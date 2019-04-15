package com.okay.thread.finalPa;

/**
 * 不变模式
 * Created by OKali on 2019/4/15.
 */
public final class Product {   // final 修饰确保无子类

    private final String no;   //  私有属性，不会被其它对象获取，final保证不会被二次赋值

    private final String name;

    private final double price;

    public Product(String no, String name, double price) { // 在创建对象时，必须指定数据，创建之后不能进行修改
        super();
        this.no = no;
        this.name = name;
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
