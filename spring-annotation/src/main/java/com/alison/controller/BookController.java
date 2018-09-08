package com.alison.controller;

import com.alison.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by OKali on 2018/8/12.
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;



}
