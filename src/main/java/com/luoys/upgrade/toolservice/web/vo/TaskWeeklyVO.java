package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class TaskWeeklyVO {
    private List<TaskDailyVO> monTaskList;
    private List<TaskDailyVO> tueTaskList;
    private List<TaskDailyVO> wedTaskList;
    private List<TaskDailyVO> thuTaskList;
    private List<TaskDailyVO> friTaskList;
    private List<TaskDailyVO> satTaskList;
    private List<TaskDailyVO> sunTaskList;
}
