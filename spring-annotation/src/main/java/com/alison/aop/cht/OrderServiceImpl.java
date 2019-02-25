package com.alison.aop.cht;

/**
 * Created by OKali on 2019/1/5.
 */
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(String username, String product) {
        Order order = new Order();
        order.setUsername(username);
        order.setProduct(product);
        return order;
    }

    @Override
    public Order queryOrder(String username) {

        Order order = new Order();
        order.setUsername("test");
        order.setProduct("test");

        return order;
    }
}
