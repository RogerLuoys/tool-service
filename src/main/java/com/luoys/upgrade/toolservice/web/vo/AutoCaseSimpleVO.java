package com.luoys.upgrade.toolservice.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 简单的用例类，用于列表展示
 *
 * @author luoys
 */
@Data
public class AutoCaseSimpleVO {

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
