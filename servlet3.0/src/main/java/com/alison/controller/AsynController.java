package com.alison.controller;

import com.alison.service.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by OKali on 2018/9/15.
 */
@Controller
public class AsynController {

    @RequestMapping("/createOrder")
    @ResponseBody
    public DeferredResult<Object> createOrder() {
        DeferredResult<Object> deferredResult = new DeferredResult<>(6000L, "error fail");
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create() {
        // 创建订单
        String order = UUID.randomUUID().toString();

        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        deferredResult.setResult(order);

        return "success==>"+order;
    }

    /**
     * 1、控制器返回Callable
     * 2、Spring异步处理，将Callable提交到TaskExecutor,使用一个隔离的线程进行执行
     * 3、DispatcherServlet和所有Filter退出web容器线程，但是response 保持打开状态
     * 4、Callable返回结果，SpringMVC将请求重现派发给容器，恢复之前的处理
     * 5、根据Callable返回的记过，SpringMVC继续进行视图渲染流程等（请求->视图渲染）
     * @return
     */
    @ResponseBody
    @RequestMapping("/async01")
    public Callable<String> async01() {

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
