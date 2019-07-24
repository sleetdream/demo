package com.example.quartz.configs;

import com.example.quartz.task.HelloTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 * 参考 https://blog.csdn.net/gnail_oug/article/details/80825302
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
                //要执行的文件
                .withIdentity("HelloTask")
                //cron表达式，每5秒执行一次，参考 https://www.cnblogs.com/dubhlinn/p/10740838.html
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .build();
    }

} 