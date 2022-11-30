package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 简单的套件类，用于列表展示
 *
 * @author luoys
 */
@Data
public class AutoSuiteSimpleVO {

    /**
     * 业务id
     */
    private Integer suiteId;

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
    private Integer ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
