package com.okay.config;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * 该对象的构造函数是希望传递的RequestContextHolder和自定义的HystrixThreadLocal.
 * Created by OKali on 2019/3/17.
 */
public class HystrixThreadCallable<S> implements Callable<S> {

    private final RequestAttributes requestAttributes;

    private final Callable<S> delegate; // 委派

    private String params;

    public HystrixThreadCallable(Callable<S> callable, RequestAttributes requestAttributes, String params) {
        this.delegate = callable;
        this.requestAttributes = requestAttributes;
        this.params = params;
    }

    @Override
    public S call() throws Exception {

        try{
            RequestContextHolder.setRequestAttributes(requestAttributes);
            HystrixThreadLocal.threadLocal.set(params);
            return delegate.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
            HystrixThreadLocal.threadLocal.remove();
        }
    }
}
