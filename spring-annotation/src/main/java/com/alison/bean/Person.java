package com.alison.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by OKali on 2018/8/12.
 */
public class Person {

    // 基本数值，SpEL(#{}),${}去取配置文件中的值
    @Value("111")
    private String id;
    @Value("111_alison")
    private String name;

    @Value("${person.pickName}")
    private String pickName;

    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    public Person() {
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pickName='" + pickName + '\'' +
                '}';
    }
}
