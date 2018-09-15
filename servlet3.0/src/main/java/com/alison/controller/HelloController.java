package com.alison.controller;

import com.alison.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by OKali on 2018/9/10.
 */
@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        String name = helloService.sayHello("hello");
        System.out.println(name);
        return "success";
    }

    @RequestMapping("/toSuccess")
    public String toSuccess() {
        return "success";
    }
}
