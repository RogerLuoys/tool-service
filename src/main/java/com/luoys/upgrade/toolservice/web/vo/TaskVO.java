package com.luoys.upgrade.toolservice.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TaskVO {

    /**
     * 业务id
     */
    private Integer taskId;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 任务结束时间（完成时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 所属人id
     */
    private Integer ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
