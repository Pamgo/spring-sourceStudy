package com.alison.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by OKali on 2018/9/15.
 */
@WebServlet(value = "/async", asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 开启异步处理
        final AsyncContext asyncContext = req.startAsync();

        // 开始异步处理
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("耗时,副线程开始。。。。");
                    job();
                    asyncContext.complete();
                    // 获取到异步上下文
                    AsyncContext context = req.getAsyncContext();

                    ServletResponse response = context.getResponse();
                    response.getWriter().write("hello async");
                    System.out.println("耗时,副线程结束。。。。");
                } catch (Exception e) {}
            }
        });
    }

    private void job() {
        System.out.println("process start ....");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
