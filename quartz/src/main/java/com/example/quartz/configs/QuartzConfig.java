package com.example.quartz.configs;

import com.example.quartz.task.HelloTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail testQuartz1() {
        return JobBuilder.newJob(HelloTask.class).withIdentity("HelloTask").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("HelloTask")//要执行的文件
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))//cron表达式，每5秒执行一次
                .build();
    }

} 