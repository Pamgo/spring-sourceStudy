package com.alison.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by OKali on 2018/8/15.
 * 创建一个Spring定义的工厂bean
 */
public class ColorFactoryBean implements FactoryBean<Color> {

    //返回一个color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        System.out.println();
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    // 是否单实例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
