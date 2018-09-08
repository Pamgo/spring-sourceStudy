package com.alison;

import com.alison.tx.TxConfig;
import com.alison.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by OKali on 2018/8/25.
 */
public class IOCTest_Tx {

    @Test
    public void test01 () {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.insertUser();
        applicationContext.close();
    }
}
