package com.alison.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * Created by OKali on 2018/8/19.
 */
@Aspect // 切面类型，告诉Spring当前类是一个切面类
public class LogAspects {

    // 抽取公共的切入点表达式
    // 1、本类引用 pointCut()
    // 2、其它的切面引用 com.alison.aop.LogAspects.pointCut()
    @Pointcut("execution(public int com.alison.aop.MathCalculator.div(int,int))")
    public void pointCut() {

    }

    // @Before在目标方法之前切入，切入点点表达式（指定在哪个方法切入）
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName()+"@Before除法运行。。。参数列表是：{"+ Arrays.asList(args)+"}");
    }

    @After("com.alison.aop.LogAspects.pointCut()")
    public void logEnd() {
        System.out.println("@After除法结束。。。");
    }

    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturen(Object result) {
        System.out.println("@AfterReturning除法正常返回。。。结算结果：{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void logException(Exception ex) {
        System.out.println("@AfterThrowing除法异常。。。异常信息：{}");
    }
}
