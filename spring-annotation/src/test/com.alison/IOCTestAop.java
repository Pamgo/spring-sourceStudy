package com.alison;

import com.alison.aop.MathCalculator;
import com.alison.config.MainConfigAop;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by OKali on 2018/8/19.
 */
public class IOCTestAop {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigAop.class);

        MathCalculator c = applicationContext.getBean(MathCalculator.class);
        int i = c.div(4, 2);

        applicationContext.close();
    }
}
