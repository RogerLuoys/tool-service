package com.luoys.upgrade.toolservice.web;


import com.alibaba.fastjson.JSON;
import com.luoys.common.api.Result;
import com.luoys.upgrade.toolservice.common.TimeUtil;
import com.luoys.upgrade.toolservice.service.TaskService;
import com.luoys.upgrade.toolservice.web.vo.TaskVO;
import com.luoys.upgrade.toolservice.web.vo.TaskWeeklyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId,
                                       @RequestBody TaskVO taskVO) {
        log.info("--->创建每日任务开始：{}", JSON.toJSONString(taskVO));
        taskVO.setOwnerId(userId);
        return Result.ifSuccess(taskService.create(taskVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("taskId") String taskId) {
        log.info("--->删除任务开始：{}", taskId);
        return Result.ifSuccess(taskService.remove(taskId));
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
    public Result<Integer> updateStatus(@RequestParam("taskId") String taskId,
                                                 @RequestParam("status") Integer status) {
        log.info("--->修改每日任务状态开始：taskId={}，status={}", taskId, status);
        return Result.ifSuccess(taskService.updateStatus(taskId, status));
    }

    @RequestMapping(value = "/updateComment", method = RequestMethod.PUT)
    public Result<String> updateComment(@RequestParam("taskId") String taskId,
                                                 @RequestParam("comment") String comment) {
        log.info("--->修改每日任务备注开始：taskId={}，comment={}", taskId, comment);
        return Result.ifSuccess(taskService.updateComment(taskId, comment));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<List<TaskVO>> query(@RequestHeader("userId") String userId,
                                                        @RequestParam(value = "startTime", required = false) Date startTime,
                                                        @RequestParam(value = "endTime", required = false) Date endTime) {
        log.info("--->按用户查询每日任务列表开始：startTime={}, endTime={}", TimeUtil.dateFormat(startTime), TimeUtil.dateFormat(endTime));
        return Result.success(taskService.query(userId, startTime, endTime));
    }

    @RequestMapping(value = "/queryByWeekly", method = RequestMethod.GET)
    public Result<List<TaskWeeklyVO>> queryByWeekly(@RequestHeader("userId") String userId,
                                                    @RequestParam(value = "currentDate", required = false) Date currentDate) {
        log.info("--->按周查询任务列表开始：currentDate={}", TimeUtil.dateFormat(currentDate));
        List<TaskWeeklyVO> weekList = taskService.queryByWeekly(userId, currentDate);
        weekList.get(5).getTaskList().addAll(weekList.get(6).getTaskList());
        weekList.remove(6);
        weekList.get(5).setWeekName("周末");
        return Result.success(weekList);
    }
}
