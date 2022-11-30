package com.luoys.upgrade.toolservice.schedule;

import com.luoys.upgrade.toolservice.service.SuiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TaskSchedule {

    @Autowired
    private SuiteService suiteService;

    // 每周日凌晨1点执行
//    @Scheduled(cron = "0 0 1 ? * 7")
    // 每5分钟执行一次，测试用
//    @Scheduled(cron = "0 0/5 * * * ?")
    public void automaticConvertWeekTask() {
        log.info("=====定时任务:套件排队开始 {}=====", new Date());
        suiteService.reset(1);
        log.info("=====定时任务:套件排队结束 {}=====", new Date());
    }
}
