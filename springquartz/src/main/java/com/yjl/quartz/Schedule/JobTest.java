package com.yjl.quartz.Schedule;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OKali on 2018/10/13.
 */
public class JobTest {

    private static final Logger logger = LoggerFactory.getLogger(JobTest.class);

    private static int count = 1;

    public void print(JobExecutionContext context) {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        String name = scheduleJob.getJobName();
        String group = scheduleJob.getJobGroup();
        String id = scheduleJob.getJobId();
        String des = scheduleJob.getDesc();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        String dateString = sd.format(new Date());
        logger.info(count+++"方法名称["+scheduleJob.getMethodName()+"]    " +dateString +"    "+id+"   "+name+"   "+group+"   "+des );
    }

    public void print1_test(JobExecutionContext context) {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        String name = scheduleJob.getJobName();
        String group = scheduleJob.getJobGroup();
        String id = scheduleJob.getJobId();
        String des = scheduleJob.getDesc();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        String dateString = sd.format(new Date());
        logger.info(count+++"方法名称["+scheduleJob.getMethodName()+"]    " +dateString +"    "+id+"   "+name+"   "+group+"   "+des );
    }

    public void print1_test3(JobExecutionContext context) {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        String name = scheduleJob.getJobName();
        String group = scheduleJob.getJobGroup();
        String id = scheduleJob.getJobId();
        String des = scheduleJob.getDesc();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        String dateString = sd.format(new Date());
        logger.info(count+++"方法名称["+scheduleJob.getMethodName()+"]    " +dateString +"    "+id+"   "+name+"   "+group+"   "+des );
    }
}
