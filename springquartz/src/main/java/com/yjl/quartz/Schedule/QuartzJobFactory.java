package com.yjl.quartz.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yjl.quartz.utils.JobUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");

		JobUtils.invokeMethod(scheduleJob,context);
	}
}