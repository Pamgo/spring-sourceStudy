package com.okay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by OKali on 2019/3/1.
 */
@RestController
public class TestController {

    @GetMapping("/test/cookie")
    public String testGateway(HttpServletRequest request , HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName()+":" + cookie.getValue());
            }
        }
        return "SpringCloud gateway, hello world!";
    }

    /**
     * 测试Head路由断言工厂
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/test/head")
    public String testGatewayHead(HttpServletRequest request, HttpServletResponse response){
        String head=request.getHeader("X-Request-okay");
        return "return head info:"+head;
    }
}
