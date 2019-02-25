package com.alison.aop.cht;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by OKali on 2019/1/5.
 */
public class LogArgsAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("准备执行方法：" + method.getName() +", 参数列表：" + Arrays.toString(args));
    }

}
