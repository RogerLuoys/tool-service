package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * task_daily
 * @author luoys
 */
@Data
public class TaskDailyPO {
    private Integer id;

    /**
     * 业务id
     */
    private String taskDailyId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String comment;

    /**
     * 1-未完成；2-已完成
     */
    private Byte status;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务结束时间（完成时间）
     */
    private Date endTime;

}
