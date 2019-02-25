package com.okay.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import java.io.IOException;

/**
 * Created by OKali on 2019/1/18.
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // 数字越小，越高
    @Override
    public int filterOrder() {
        return 0;
    }

    // 某些条件发生的时候才需要执行
    @Override
    public boolean shouldFilter() {
        return true;  // true永远执行
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext(); // 请求上下文

        try {
            ServletInputStream inputStream = ctx.getRequest().getInputStream();
            log.info("request info:{}", IOUtils.toString(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ctx.set("startTime", System.currentTimeMillis());

        return null;
    }
}
