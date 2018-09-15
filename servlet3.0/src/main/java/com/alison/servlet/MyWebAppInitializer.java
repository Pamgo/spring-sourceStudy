package com.alison.servlet;

import com.alison.config.AppConfig;
import com.alison.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by OKali on 2018/9/10.
 */
// web容器启动的时候创建对象，调用方法来初始化容器以及前段控制器
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 获取根容器配置类，（Spring的配置文件），父容器
    //对应web.xml配置<!-- Spring配置 -->
    /*<!-- ====================================== -->
            <listener>
            <listenerclass>
            org.springframework.web.context.ContextLoaderListener
            </listener-class>
    </listener>
    <!-- 指定Spring Bean的配置文件所在目录。默认配置在WEB-INF目录下 -->
    <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:config/applicationContext.xml</param-value>
    </context-param>*/
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    // 获取web容器的配置类（SpringMVC配置文件） 子容器
    // 对应web.xml的
//       /* <!-- Spring MVC配置 -->
//                <!-- ====================================== -->
//                <servlet>
//                <servlet-name>spring</servlet-name>
//                <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
//    <!-- 可以自定义servlet.xml配置文件的位置和名称，默认为WEB-INF目录下，名称为[<servlet-name>]-servlet.xml，如spring-servlet.xml
//    <init-param>
//    <param-name>contextConfigLocation</param-name>
//    <param-value>/WEB-INF/spring-servlet.xml</param-value>&nbsp; 默认
//    </init-param>
//            -->
//    <load-on-star*/tup>1</load-on-startup>
//</servlet>
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    // 获取DispatcherServlet的映射信息
    // "/",拦截所有请求（包括静态资源（xxx.js,xxx.png)，但是不包括*.jsp）
    // "/*",拦截所有请求，包括*.jsp
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
