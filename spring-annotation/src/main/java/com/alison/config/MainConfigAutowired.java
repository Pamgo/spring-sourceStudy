package com.alison.config;

import com.alison.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by OKali on 2018/8/17.
 * 自动装配：
 *    spring利用依赖注入（DI）,完成对IOC容器中各个组件的依赖关系赋值
 *
 *  1)、@Autowired,自动注入（方法，参数，属性，构造器）
 *      1)、默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookDao.class)
 *      2)、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
 *                                  applicationContext.getBean("bookDao")
 *      3)、@Qualifier("bookDao"),使用@Qualifier指定需要装配的组件的id，而不是使用属性名
 *      4)、自动装配默认一定要将属性值装配好，没有就会报错
 *         可以使用@Autowired(required=false)
 *      5)、@Primary,让Spring进行自动装配的时候，默认使用首选的bean
 *         也可以继续使用@Qualifier指定需要装配的bean的名字
 *      6）、如果组件只有一个有参构造器时，这个有参构造器的@Qutowired可以省略
 *      7）、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext,BeanFactory,xxx)
 *           自定义组件实现xxxAware，在创建对象的时候，会调用接口规定的方法注入相关组件，
 *           xxxAware,使用xxxProcessor,
 *                ApplicationContextAware==>ApplicationContextAwareProcessor
 *
 *
 *
 *   2）、Spring支持使用@Resource(JSR250)和@Inject(JSR250)[Java规范的注解]
 *      @Resource:
 *       可以和@Autowired一样实现自动装配，默认按照组件名称进行装配的
 *       但是没有能支持@Primary功能
 *       @Inject：
 *       需要导入javax.inject.Inject包
 * AutowiredAnnotationBeanPostProcessor 完成自动装配的功能
 */
@Configuration
@ComponentScan(value = {"com.alison.dao","com.alison.service","com.alison.controller"})
public class MainConfigAutowired {

    @Primary // 首选装配
    @Bean("bookDao2")
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }
}
