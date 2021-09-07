package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 简单的步骤类，用于列表展示
 *
 * @author luoys
 */
@Data
public class AutoStepSimpleVO {

    /**
     * 业务id
     */
    private String stepId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 步骤类型：1 SQL，2 HTTP，3 RPC，4 UI
     */
    private Integer type;

    /**
     * 实际结果取到后的等待时间
     */
    private Integer afterSleep;

    /**
     * 断言类型：1 完全匹配，2 模糊匹配，-1 不校验
     */
    private Integer assertType;

    /**
     * 实际结果
     */
    private String assertActual;

    /**
     * 预期结果
     */
    private String assertExpect;

    /**
     * 断言结果
     */
    private Boolean assertResult;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    /**
     * 是否公用
     */
    private Boolean isPublic;

}
