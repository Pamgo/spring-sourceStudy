package com.yjl.quartz.utils;

import com.yjl.quartz.Schedule.ScheduleJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by OKali on 2018/10/13.
 */
public class JobUtils {

    private static final Logger logger = LoggerFactory.getLogger(JobUtils.class);

    public static final String STATUS_RUNNING = "1"; // 启动状态

    public static final String STATUS_NOT_RUNNING = "0"; // 未启动状态

    public static final String CONCURRENT_IS = "1";

    public static final String CONCURRENT_NOT = "0";

    public static void invokeMethod(ScheduleJob scheduleJob, JobExecutionContext context) {
        Object obj = null;
        Class clazz = null;
        if(StringUtils.isNotBlank(scheduleJob.getSpringId())) {
            obj = SpringUtils.getBean(scheduleJob.getSpringId());
        } else if(StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                obj = clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        if (null == obj) {
            logger.error("任务名称 = [" +scheduleJob.getJobName()+ "]----未启动成功，请检查是否配置！！！");
            return;
        }
        clazz = obj.getClass();
        Method method = null;

        try {
            method = clazz.getMethod(scheduleJob.getMethodName(), new Class[] {
                    JobExecutionContext.class
            });
        } catch (NoSuchMethodException e) {
            logger.error("任务名称 = [" +scheduleJob.getJobName()+ "]---未启动成功，方法设置错误！！", e);
        }

        if (method != null) {
            try {
                method.invoke(obj, new Object[]{context});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        logger.info("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功");
    }
}
