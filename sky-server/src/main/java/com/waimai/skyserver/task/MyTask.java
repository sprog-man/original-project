package com.waimai.skyserver.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;


/**
 * 任务定时器
 */
@Component
@Slf4j
public class MyTask {

    /**
     * 每隔5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        log.info("定时任务开始:{}", LocalDateTime.now());
    }

}
