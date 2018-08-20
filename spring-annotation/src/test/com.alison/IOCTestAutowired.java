package com.alison;

import com.alison.config.MainConfigAutowired;
import com.alison.dao.BookDao;
import com.alison.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by OKali on 2018/8/17.
 */
public class IOCTestAutowired {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigAutowired.class);

    @Test
    public void test01() {
        BookService bean = context.getBean(BookService.class);
        System.out.println(bean);

       /* BookDao bean1 = context.getBean(BookDao.class);
        System.out.println(bean1);*/

        context.close();
    }
}
