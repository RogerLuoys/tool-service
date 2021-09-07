package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 全量的用例类
 *
 * @author luoys
 */
@Data
public class AutoCaseVO {

    /**
     * 业务id
     */
    private String caseId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 1 接口，2 UI
     */
    private Integer type;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 用例执行的最长时间
     */
    private Integer maxTime;

    /**
     * 用例执行环境（参数）
     */
    private String environment;

    /**
     * 用例计划完成时间
     */
    private Date finishTime;

    /**
     * 前置步骤
     */
    private List<CaseStepVO> preStepList;

    /**
     * 主要步骤
     */
    private List<CaseStepVO> mainStepList;

    /**
     * 收尾步骤
     */
    private List<CaseStepVO> afterStepList;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
