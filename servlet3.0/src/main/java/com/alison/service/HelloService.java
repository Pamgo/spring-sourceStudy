package com.alison.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Created by OKali on 2018/9/10.
 */
@Service
public class HelloService {

    public  String sayHello(String name) {
        return name;
    }
}
