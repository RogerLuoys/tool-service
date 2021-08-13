package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.List;

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
     * 用例执行的最长时间
     */
    private Integer maxTime;

    /**
     * 1 接口，2 UI
     */
    private Integer type;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 前置步骤，List<ParamDTO>的json格式
     */
    private List<CaseStepVO> preStepList;

    /**
     * 主要步骤，List<ParamDTO>的json格式
     */
    private List<CaseStepVO> mainStepList;

    /**
     * 收尾步骤，List<ParamDTO>的json格式
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
