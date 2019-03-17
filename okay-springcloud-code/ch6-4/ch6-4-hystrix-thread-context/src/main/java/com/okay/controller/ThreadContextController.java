package com.okay.controller;

import com.okay.config.HystrixThreadLocal;
import com.okay.service.IThreadContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by OKali on 2019/3/17.
 */
@RestController
public class ThreadContextController {

    private static final Logger logger = LoggerFactory.getLogger(ThreadContextController.class);

    @Autowired
    private IThreadContextService threadContextService;

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") Integer id) {
        // 第一种测试，放入上下文对象
        HystrixThreadLocal.threadLocal.set("userId: " + id);

        // 第二种测试，利用RequestContextHolder放入对象测试
        RequestContextHolder.currentRequestAttributes().setAttribute("userId", "userId: " + id, RequestAttributes.SCOPE_REQUEST);
        logger.info("ThreadContextController, Current thread: " + Thread.currentThread().getId());
        logger.info("ThreadContextController, Thread local: " + HystrixThreadLocal.threadLocal.get());
        logger.info("ThreadContextController, RequestContextHolder:" + RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST));

        String user = threadContextService.getUser(id);
        return user;
    }

}
