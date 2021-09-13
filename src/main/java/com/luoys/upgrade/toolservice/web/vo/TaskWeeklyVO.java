package com.luoys.upgrade.toolservice.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskWeeklyVO {
    private String weekName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date weekDate;
    private List<TaskVO> taskList;

//    private List<TaskDailyVO> monTaskList;
//    private List<TaskDailyVO> tueTaskList;
//    private List<TaskDailyVO> wedTaskList;
//    private List<TaskDailyVO> thuTaskList;
//    private List<TaskDailyVO> friTaskList;
//    private List<TaskDailyVO> satTaskList;
//    private List<TaskDailyVO> sunTaskList;
}
