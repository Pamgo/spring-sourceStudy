package com.alison.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/**
 * Created by OKali on 2018/9/15.
 */
@Controller
public class AsyncController {

    @ResponseBody
    @RequestMapping("/async01")
    public Callable<String> async01 (){

        System.out.println("主线程开始。。。" + Thread.currentThread()+"==>" + System.currentTimeMillis());

        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("父线程开始。。。" + Thread.currentThread()+"==>" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("父线程结束。。。" + Thread.currentThread()+"==>" + System.currentTimeMillis());
                return "Callable<String> aysnc01";
            }
        };
        System.out.println("主线程结束。。。" + Thread.currentThread()+"==>" + System.currentTimeMillis());

        return  callable;
    }
}
