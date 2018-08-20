package com.alison.config;

import com.alison.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by OKali on 2018/8/15.
 * bean的声明周期，
 *      bean创建--》初始化---》销毁的过程
 * 容器管理bean的声明周期
 * 我们可以自定义初始化和销毁方法，容器在bean进行到当前声明周期的时候来调用我们自定义的初始化和销毁方法
 *
 * 构造（对象创建）
 *      单实例：在容器启动的时候创建对象
 *      多实例：调用的时候创建对象
 *
 * @see BeanPostProcessor#postProcessBeforeInitialization()
 * 初始化：对象创建完成并赋值好之后调用初始化方法
 *       自定义实例生命周期可以做一些逻辑处理（比如数据源等）
 * @see BeanPostProcessor#postProcessAfterInitialization()
 *
 * 销毁：
 *  单实例：容器关闭的时候进行销毁
 *  多实例：容器不会管理这个bean，容器不会调用销毁方法
 *  =============================================================================
 *  BeanPostProcessor原理：
 *  遍历得到容器中所有的BeanPostProcessor，这个执行beforeInitialization
 *  一旦返回null，跳出for循环，不会执行
 *  populateBean(beanName, mbd, instanceWrapper); // bean进行属性赋值
 *  exposedObject = initializeBean(beanName, exposedObject, mbd); // 准备bean初始化
 *   {
 *     wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *     invokeInitMethods(beanName, wrappedBean, mbd); // 执行初始化
 *     wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *  }
 *  =============================================================================
 * 1）、指定初始化和销毁方法
 *      通过@Bean指定init-method / destroy-method
 * 2)、通过让Bean实现InitializingBean（定义初始化逻辑），
 *     DisposableBean(定义销毁逻辑)
 * 3)、可以使用JSR250，
 *    @PostConstruct , 在bean创建完成并且属性赋值完成，来执行初始化
 *    @PreDestroy , 在bean从容器中移除之前回调
 * 4)、BeanPostProcessor, bean的后置处理器
 *   在bean初始化前后进行一些处理工作
 *   postProcessBeforeInitialization : 在初始化之前调用
 *   postProcessAfterInitialization  : 在初始化之后调用
 *
 * Spring底层对BeanPostProcessor的使用，
 *   bean赋值，注入其它组件，@Autowired, 生命周期注解功能，@Async,xxx
 */
@ComponentScan("com.alison.bean")
@Configuration
public class MainConfigOfLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
