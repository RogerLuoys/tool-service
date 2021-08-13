package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class AutoSuiteVO {

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
     * 用例列表，List<ParamDTO>的json格式（最多放100-200个case）
     */
    private List<SuiteCaseVO> caseList;

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

}
