package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * auto_suite
 *
 * @author luoys
 */
@Data
public class AutoSuitePO {

    private Integer id;

    /**
     * 业务id
     */
    private String suiteId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 套件状态：1 空闲，2 执行中
     */
    private Integer status;

    /**
     * 用例执行的环境
     */
    private String environment;

    /**
     * 入参列表，List<CommonDTO>类型
     */
    private String parameter;

    /**
     * 步骤间的等待时间
     */
    private Integer stepSleep;

    /**
     * 用例执行的最长时间
     */
    private Integer caseMaxTime;

    /**
     * 用例总数
     */
    private Integer total;

    /**
     * 成功用例数
     */
    private Integer passed;

    /**
     * 失败用例数
     */
    private Integer failed;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    /**
     * ui用例是否执行完成
     */
    private Boolean isUiCompleted;

    /**
     * api用例是否执行完成
     */
    private Boolean isApiCompleted;

}
