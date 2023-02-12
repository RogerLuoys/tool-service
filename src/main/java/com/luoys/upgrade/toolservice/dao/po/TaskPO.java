package com.luoys.upgrade.toolservice.dao.po;

import java.util.Date;
import lombok.Data;

/**
 * task
 * @author luoys
 */
@Data
public class TaskPO {
    private Integer id;

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

    /**
     * 所属人id
     */
    private Integer ownerId;

}
