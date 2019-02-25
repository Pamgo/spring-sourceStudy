package com.alison.aop.cht;

/**
 * Created by OKali on 2019/1/5.
 * 阅读源码分析用
 */
public interface OrderService {

    Order createOrder(String username, String product);

    Order queryOrder(String username);
}
