package com.okay.kafka;

/**
 * Created by OKali on 2019/3/24.
 */
public class Customer {

    private int customerId;

    private String customerName;

    public Customer(int id, String name) {
        this.customerId = id;
        this.customerName = name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}
