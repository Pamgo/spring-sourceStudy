package com.alison.service;

import com.alison.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by OKali on 2018/8/12.
 */
@Service
public class BookService {

    //@Qualifier("bookDao") // 明确指定那个组件
    //@Autowired(required = false)
    @Resource
    public BookDao bookDao2;

    public void prinit() {
        System.out.println(bookDao2);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao2 +
                '}';
    }
}
