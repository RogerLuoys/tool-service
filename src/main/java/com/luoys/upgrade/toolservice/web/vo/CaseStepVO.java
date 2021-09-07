package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 用例下关联的步骤类
 *
 * @author luoys
 */
@Data
public class CaseStepVO {

    /**
     * 用例的业务id
     */
    private String caseId;

    /**
     * 步骤的业务id
     */
    private String stepId;

    /**
     * 步骤执行顺序
     */
    private Integer sequence;

    /**
     * 1 前置步骤，2 主要步骤，3 收尾步骤
     */
    private Integer type;

    /**
     * 步骤详情
     */
    private AutoStepVO autoStep;

}
