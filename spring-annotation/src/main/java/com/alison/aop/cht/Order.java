package com.alison.aop.cht;

/**
 * Created by OKali on 2019/1/5.
 */
public class Order {

    private String username;

    private String product;

    public void setProduct(String product) {
        this.product = product;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct() {
        return product;
    }

    public String getUsername() {
        return username;
    }
}
