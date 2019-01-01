package com.yjl.quartz.service;

import com.yjl.quartz.Schedule.QuartzJobFactory;
import com.yjl.quartz.Schedule.ScheduleJob;
import com.yjl.quartz.utils.JobUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by OKali on 2018/10/13.
 */
@Service
public class JobTaskService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private static final Logger logger = LoggerFactory.getLogger(JobTaskService.class);

    public List<ScheduleJob> getAllTask() {
        // TODO 从数据库中获取所有任务

        return null;
    }

    public void addTask(ScheduleJob scheduleJob) {
        // TODO 添加到数据库中
    }

    public ScheduleJob getTaskById(Long jobId) {
        // TODO 从数据库中查询一条任务
        return null;
    }

    public void changeStatus(Long jobId, String cmd) throws SchedulerException {
       ScheduleJob scheduleJob = getTaskById(jobId);
       if (null == scheduleJob) {
           return;
       }
       if ("stop".equals(cmd)) {
           deleteJob(scheduleJob);
           scheduleJob.setJobStatus(JobUtils.STATUS_NOT_RUNNING);
       } else if ("start".equals(cmd)) {
           scheduleJob.setJobStatus(JobUtils.STATUS_RUNNING);
           // 添加任务
           addJob(scheduleJob);
       }

       // TODO 更新数据库任务状态
    }

    public void updateCron(Long jobId, String cron) throws SchedulerException {
        ScheduleJob scheduleJob = getTaskById(jobId);
        if (null == scheduleJob) {
            return;
        }
        scheduleJob.setCronExpression(cron);
        if (JobUtils.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            updateJobCron(scheduleJob);
        }
        // TODO 更新数据库任务
    }

    public void addJob(ScheduleJob scheduleJob) throws SchedulerException {
        if (null == null || !JobUtils.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        logger.info(scheduler + "....................................add");
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(),scheduleJob.getJobGroup())
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,trigger);
        } else {
            // Trigger 已经存在，则更新
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(scheduleJob.getJobName(),scheduleJob.getJobGroup())
                    .withSchedule(cronScheduleBuilder).build();
            // 按照新的表达式从新设置job执行
            scheduler.rescheduleJob(triggerKey,trigger);
        }
    }

    /***************************************************************************/
    /************************************获取任务列表*****************************/

    /**
     * 获取所有计划中的任务列表
     * @return
     */
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> scheduleJobs = new ArrayList<>(jobKeys.size());
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggersOfJob) {
                ScheduleJob scheduleJob = new ScheduleJob();
                scheduleJob.setJobName(jobKey.getName());
                scheduleJob.setJobGroup(jobKey.getGroup());
                scheduleJob.setDesc("触发器：" + trigger.getDescription());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                scheduleJob.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    scheduleJob.setCronExpression(cronExpression);
                }
                scheduleJobs.add(scheduleJob);
            }
        }

        return scheduleJobs;
    }

    /**
     * 获取所有正在运行的任务
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> scheduleJobs = new ArrayList<>(currentlyExecutingJobs.size());
        for (JobExecutionContext executionContext : currentlyExecutingJobs) {
            ScheduleJob scheduleJob = new ScheduleJob();
            JobDetail jobDetail = executionContext.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executionContext.getTrigger();

            scheduleJob.setJobName(jobKey.getName());
            scheduleJob.setJobGroup(jobKey.getGroup());
            scheduleJob.setDesc("触发器：" + trigger.getDescription());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            scheduleJob.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                scheduleJob.setCronExpression(cronExpression);

            }
            scheduleJobs.add(scheduleJob);
        }

        return scheduleJobs;
    }

    /**
     * 暂停一个job
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                .withSchedule(cronScheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey,trigger);

    }

    @PostConstruct
    public void init() throws SchedulerException {
        // TODO 从数据库中获取所有任务
        List<ScheduleJob> scheduleJobs = null;
        if (!CollectionUtils.isEmpty(scheduleJobs)) {
            for (ScheduleJob scheduleJob : scheduleJobs) {
                addJob(scheduleJob);
            }
        }

    }
}
