package io.spring.boot.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务配置类: Spring自带的定时任务
 */
@Configuration
@EnableScheduling // 启用定时任务
public class ScheduledTaskConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0/10 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... ");
    }
}