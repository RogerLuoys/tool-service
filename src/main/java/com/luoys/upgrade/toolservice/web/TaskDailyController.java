package com.luoys.upgrade.toolservice.web;

import com.alibaba.fastjson.JSON;
import com.luoys.common.api.Result;
import com.luoys.upgrade.toolservice.service.TaskDailyService;
import com.luoys.upgrade.toolservice.web.vo.TaskDailyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/taskDaily")
public class TaskDailyController {

    @Autowired
    private TaskDailyService taskDailyService;

    @RequestMapping(value = "/newTaskDaily", method = RequestMethod.POST)
    public Result<String> newTaskDaily(@RequestBody TaskDailyVO taskDailyVO) {
        log.info("=====》创建每日任务开始：{}", JSON.toJSONString(taskDailyVO));
        return Result.ifSuccess(taskDailyService.newTaskDaily(taskDailyVO));
    }

    @RequestMapping(value = "/modifyTaskDailyStatus", method = RequestMethod.PUT)
    public Result<Integer> modifyTaskDailyStatus(@RequestParam("taskDailyId") String taskDailyId,
                                                 @RequestParam("status") Integer status) {
        log.info("====》修改每日任务状态开始：taskDailyId={}，status={}", taskDailyId, status);
        return Result.ifSuccess(taskDailyService.modifyTaskDailyStatus(taskDailyId, status));
    }

    @RequestMapping(value = "/modifyTaskDailyComment", method = RequestMethod.PUT)
    public Result<String> modifyTaskDailyComment(@RequestParam("taskDailyId") String taskDailyId,
                                                 @RequestParam("comment") String comment) {
        log.info("====》修改每日任务备注开始：taskDailyId={}，comment={}", taskDailyId, comment);
        return Result.ifSuccess(taskDailyService.modifyTaskDailyComment(taskDailyId, comment));
    }

    @RequestMapping(value = "/queryTaskDailyList", method = RequestMethod.GET)
    public Result<List<TaskDailyVO>> queryTaskDailyList(@RequestHeader("userId") String userId,
                                                        @RequestParam(value = "startTime", required = false) Date startTime,
                                                        @RequestParam(value = "endTime", required = false) Date endTime) {
        log.info("====》按用户查询每日任务列表开始");
        return Result.ifSuccess(taskDailyService.queryUserTaskDaily(userId, startTime, endTime));
    }
}
