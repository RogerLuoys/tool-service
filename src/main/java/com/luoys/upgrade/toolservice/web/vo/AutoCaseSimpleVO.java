package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;


@Data
public class AutoCaseSimpleVO {

    private Integer caseId;

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
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
