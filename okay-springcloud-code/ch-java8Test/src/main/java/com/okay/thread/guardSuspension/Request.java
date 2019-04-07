package com.okay.thread.guardSuspension;

/**
 * 模拟请求
 * Created by OKali on 2019/4/7.
 */
public class Request {

    private String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[ request " + name +" ]";
    }
}
