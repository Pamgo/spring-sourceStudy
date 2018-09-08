package com.alison.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by OKali on 2018/8/25.
 * 声明式事务：
 * 1、导入相关依赖
 *  数据源，数据库驱动，Spring-jdbc模块
 * 2、配置数据源、JdbcTemplate(Spring提供的简化数据库操作的工具）操作数据
 * 3、给方法加上@Transactional 表示当前方法是一个事务方法
 * 4、@EnableTransactionManagement 开启一个注解的事务管理功能
 *      @EnableXXX 开启某一个功能
 * 5、配置事务管理器 PlatformTransactionManager
 *
 *
 *
 *
 *  原理：
 *    1）、@EnableTransactionManagement
 *      @Import({TransactionManagementConfigurationSelector.class})给容器中导入组件
 *      导入两个组件 AutoProxyRegistrar、ProxyTransactionManagementConfiguration
 *    2）、AutoProxyRegistrar：
 *              给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件
 *              利用后置处理器机制在对象创建以后，返回一个代理对象（增强器），代理对象执行方法利用拦截器进行拦截
 *    3）、InfrastructureAdvisorAutoProxyCreator做了什么？
 *          1.给容器中注册事务增强器
 *              1.1、事务增强器调用事务注解的信息，AnnotationTransactionAttributeSource 解析事务过程
 *              1.2、事务拦截器 transactionInterceptor，保存了事务属性信息，事务管理器
 *                  它一个 MethodInterceptor：
 *                  在目标方法执行的时候：
 *                      执行拦截器链：
 *                        事务拦截器
 *                              1.先获取事务属性
 *                              2.再获取platFormTransactionManager,如果事先没有添加执行任何创建transactionManager，最终
 *                                会从容器中获取一个再获取platFormTransactionManager
 *                              3.执行目标方法：
 *                                  如果异常，获取到事务管理器，利用事务管理器回滚操作
 *                                  如果正常，利用事务管理器提交事务
 */
@EnableTransactionManagement
@ComponentScan("com.alison.tx")
@Configuration
public class TxConfig {

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mytestdb");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        // Spring对@Configuration类会特殊处理，给容器中加组件的方法，多次调用都只是从容器中找组件而已
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    // 注册事务管理器在容器中
    @Bean
    public PlatformTransactionManager transactionManager () throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }

}
