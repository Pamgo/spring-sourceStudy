package com.alison.config;

import com.alison.bean.Color;
import com.alison.bean.ColorFactoryBean;
import com.alison.bean.Person;
import com.alison.bean.Red;
import com.alison.condition.LinuxCondition;
import com.alison.condition.MyImportBeanDefinitionRegistrar;
import com.alison.condition.MyImportSelector;
import com.alison.condition.WindowsCondition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * Created by OKali on 2018/8/12.
 */
// 类中组件统一设置，满足当前条件，这个类中胚子的所有bean注册才能生效
@Conditional({WindowsCondition.class})
@Configuration
@Import({Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})  // 快速导入组件，id默认是组件的全类名
public class MainConfig2 {

    /**
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     * @return\
     *
     * @Scope 使用该组件调整作用域
     *
     * prototype ,多实例,ioc容器启动并不会去调用方法创建对象放到容器中
     *               每次获取的时候才会调用方法创建对象
     * singleton,单实例（默认值），ioc容器启动该会调用方法创建对象放到ioc容器中
     *              以后每次获取就是直接从容器（map.get()）中获取
     * request,同一个请求创建
     * session，通过一个session创建
     * 懒加载：
     *  单例beanm默认，默认在容器启动的时候创建对象
     *  懒加载，容器启动不创建对象，第一次使用（获取）Bean创建对象，并初始化
     */
  //  @Scope
    @Lazy
    @Bean
    public Person person() {
        System.out.println("给容器中添加person。。。。。");
        return new Person("2","pamgo");
    }


    /**
     * @Conditional({condition}),按照一定的调节进行判断，满足调节给容器中注册bean
     *
     */
    @Bean("bill")
    public Person person01() {
        return new Person("bg1","Bill Gates");
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02() {
        return new Person("l2","linus");
    }

    /**
     * 给容器注册组件，
     * 1）、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[局限于自己写]
     * 2)、@Bean[导入的第三方包里面的组件]
     * 3）、@Import[直接快速给容器中倒入一个组件]
     *      1)、@Import(要导入容器中的组件)，id为全类名
     *      2）、ImportSelector：返回需要导入的组件的全类名数组
     *      3)、ImportBeanDefinitionRegistrar : 手动给容器注册bean
     *      4)、使用Spring提供的FactoryBean(工厂Bean）
     *          a、默认获取到的是工厂bean调用getObject创建的对象
     *          b、要获取工厂Bean本身，我们需要给id前面加一个&符号
     */
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }

}
